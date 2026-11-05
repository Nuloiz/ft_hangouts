package com.example.ft_hangouts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.example.ft_hangouts.db.ContactDao
import com.example.ft_hangouts.db.Message
import com.example.ft_hangouts.db.MessageDao
import com.example.ft_hangouts.db.MessageDirection

fun getPhoneNumberSuffix(phone: String): String {
    val digitsOnly = phone.replace(Regex("[^0-9]"), "")
    return if (digitsOnly.length >= 8) digitsOnly.takeLast(8) else digitsOnly
}
class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            val messageDao = MessageDao(context)
            val contactDao = ContactDao(context)
            val allContacts = contactDao.getAllContacts()

            for (sms in messages) {
                val rawSender = sms.originatingAddress ?: continue
                val senderSuffix = getPhoneNumberSuffix(rawSender)
                val body = sms.messageBody ?: continue
                println("TRACE_SMS: Eingehende SMS von Nummer: '$senderSuffix'") //DEBUG Message
                val matchedContact = allContacts.find { contact ->
                    val contactSuffix = getPhoneNumberSuffix(contact.phoneNumber)
                    contactSuffix == senderSuffix
                }
                if (matchedContact != null) {
                    println("TRACE_SMS: Kontakt gefunden! ID: ${matchedContact.id} (${matchedContact.name}). Speichere Nachricht...")
                    val newMessage = Message(
                        contactId = matchedContact.id,
                        body = body,
                        timestamp = System.currentTimeMillis(),
                        direction = MessageDirection.RECEIVED
                    )
                    messageDao.insMessage(newMessage)
                } else {
                    println("TRACE_SMS: Kein Kontakt für Suffix '$senderSuffix' in DB gefunden!")
                }
            }
        }
    }
}