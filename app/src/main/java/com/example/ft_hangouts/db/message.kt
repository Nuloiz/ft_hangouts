package com.example.ft_hangouts.db

object MessageDirection {
    const val SENT = "Sender"
    const val RECEIVED = "Receiver"
}
data class Message(
    val id: Long = 0L,
    val contactId: Long,
    val body: String,
    val timestamp: Long,
    val direction: String
)
