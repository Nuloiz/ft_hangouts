package com.example.ft_hangouts.ui

sealed class AppScreens(val route: String) {
    object Start : AppScreens("start")
    object Main : AppScreens("main")
    object Settings : AppScreens("settings")
    object ContactList : AppScreens("contactlist")
    data class Contact(val selectedContact: com.example.ft_hangouts.db.Contact) : AppScreens("contact")
    data class Chat(val selectedContact: com.example.ft_hangouts.db.Contact) : AppScreens("contact")
}