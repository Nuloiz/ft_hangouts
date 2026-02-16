package com.example.ft_hangouts.db

import android.content.ContentValues
import android.content.Context

class MessageDao(context: Context)
{
    private val dbHelper = DatabaseHelper(context)
    fun getMessagesForContact(contactId: Long): List<Message>
    {
        val db = dbHelper.readableDatabase
        val messages = mutableListOf<Message>()
        val cursor = db.query(
            DatabaseHelper.TABLE_MESSAGES,
            null,
            "${DatabaseHelper.COLUMN_MESSAGE_CONTACT_ID} = ?",
            arrayOf(contactId.toString()),
            null,
            null,
            "${DatabaseHelper.COLUMN_MESSAGE_TIMESTAMP} ASC"
        )
        cursor.use {
            while (it.moveToNext()) {
                val id = it.getLong(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MESSAGE_ID))
                val body = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MESSAGE_BODY))
                val timestamp = it.getLong(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MESSAGE_TIMESTAMP))
                val direction = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MESSAGE_DIRECTION))

                messages.add(
                    Message(
                        id = id,
                        contactId = contactId,
                        body = body,
                        timestamp = timestamp,
                        direction = direction
                    )
                )
            }
        }
        return messages
    }

    fun insertMessage(message: Message): Long
    {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_MESSAGE_CONTACT_ID, message.contactId)
            put(DatabaseHelper.COLUMN_MESSAGE_BODY, message.body)
            put(DatabaseHelper.COLUMN_MESSAGE_TIMESTAMP, message.timestamp)
            put(DatabaseHelper.COLUMN_MESSAGE_DIRECTION, message.direction)
        }
        return db.insert(DatabaseHelper.TABLE_MESSAGES, null, values)
    }

    fun deleteMessagesForContact(contactId: Long): Int
    {
        val db = dbHelper.writableDatabase
        return db.delete(
            DatabaseHelper.TABLE_MESSAGES,
            "${DatabaseHelper.COLUMN_MESSAGE_CONTACT_ID} = ?",
            arrayOf(contactId.toString())
        )
    }
}
