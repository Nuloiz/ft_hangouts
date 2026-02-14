package com.example.fthangouts.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val contactId: Long,
    val body: String,
    val timestamp: Long,
    val direction: String // "IN" oder "OUT"
)
