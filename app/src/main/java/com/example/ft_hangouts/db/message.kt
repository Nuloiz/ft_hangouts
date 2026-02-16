package com.example.ft_hangouts.db

data class Message(
    val id: Long = 0L,
    val contactId: Long,
    val body: String,
    val timestamp: Long,
    val direction: String // Receiver or Sender
)
