package com.example.ft_hangouts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.example.ft_hangouts.db.Contact
import com.example.ft_hangouts.db.ContactDao
import com.example.ft_hangouts.db.Message
import com.example.ft_hangouts.db.MessageDao
import com.example.ft_hangouts.db.MessageDirection

fun getPhoneNumberSuffix(phone: String): String {
    val digitsOnly = phone.replace(Regex("[^0-9]"), "")
    return if (digitsOnly.length >= 8) digitsOnly.takeLast(8) else digitsOnly
}

fun formatToValidPhoneNumber(rawSender: String): String {
    val digitsOnly = rawSender.replace(Regex("[^0-9]"), "")
    return "+$digitsOnly"
}

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            val messageDao = MessageDao(context)
            val contactDao = ContactDao(context)
            val allContacts = contactDao.getAllContacts().toMutableList()
            for (sms in messages) {
                val rawSender = sms.originatingAddress ?: continue
                val senderSuffix = getPhoneNumberSuffix(rawSender)
                val body = sms.messageBody ?: continue
                println("TRACE_SMS: Eingehende SMS von Nummer: '$senderSuffix'")
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
                    println("TRACE_SMS: Kein Kontakt für Suffix '$senderSuffix' in DB gefunden! Erstelle neuen Kontakt...")
                    val validPhone = formatToValidPhoneNumber(rawSender)
                    val newContact = Contact(
                        name = validPhone,
                        phoneNumber = validPhone,
                        email = null,
                        address = null,
                        note = null,
                        pictureurl = null,
                    )
                    val newId = contactDao.insertContact(newContact)
                    val savedContact = newContact.copy(id = newId)
                    allContacts.add(savedContact)
                    val newMessage = Message(
                        contactId = newId,
                        body = body,
                        timestamp = System.currentTimeMillis(),
                        direction = MessageDirection.RECEIVED
                    )
                    messageDao.insMessage(newMessage)
                }
            }
        }
    }
}