package com.example.ft_hangouts.db

data class Contact(
    val id: Long = 0L,
    val name: String,
    val phoneNumber: String,
    val email: String?,
    val address: String?,
    val note: String?,
    val picture_url: String?
)

