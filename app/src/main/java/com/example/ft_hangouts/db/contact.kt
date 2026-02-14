package com.example.fthangouts.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val phoneNumber: String,
    val email: String,
    val address: String,
    val note: String
)
