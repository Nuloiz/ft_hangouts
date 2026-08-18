package com.example.ft_hangouts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.example.ft_hangouts.db.Contact
import com.example.ft_hangouts.db.ContactDao
import com.example.ft_hangouts.db.DatabaseHelper
import com.example.ft_hangouts.db.Message
import com.example.ft_hangouts.db.MessageDao
import com.example.ft_hangouts.db.MessageDirection

class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            val messageDao = MessageDao(context)
            val contactDao = ContactDao(context)

            for (sms in messages) {
                val senderNumber = sms.originatingAddress ?: continue
                val body = sms.messageBody ?: continue
                println("TRACE_SMS: Eingehende SMS von Nummer: '$senderNumber'") //DEBUG Message
                val contact = contactDao.getContactByPhone(senderNumber)
                if (contact != null) {
                    println("TRACE_SMS: Kontakt gefunden! ID: ${contact.id}. Speichere Nachricht...") //DEBUG Message
                    val newMessage = Message(
                        contactId = contact.id,
                        body = body,
                        timestamp = System.currentTimeMillis(),
                        direction = MessageDirection.RECEIVED
                    )
                    messageDao.insMessage(newMessage)
                } else {
                    println("TRACE_SMS: Kein Kontakt für Nummer '$senderNumber' in DB gefunden!")//DEBUG Message
                }
            }
        }
    }
}