package com.example.ft_hangouts.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.ft_hangouts.db.ContactDao
import com.example.ft_hangouts.ui.AppScreens.*

@Composable
fun AppScreenRouter(
    currentScreen: AppScreens,
    onNavigate: (AppScreens) -> Unit,
    contactDao: ContactDao)
{
    when (currentScreen)
    {
        Start -> StartScreen(contactDao = contactDao, onNavigateToMain = { onNavigate(Main)}, onNavigateToContactList = { onNavigate(ContactList)}, onNavigateToSettings = { onNavigate(Settings)})
        Main -> MainScreen(contactDao = contactDao, onNavigateToSettings = { onNavigate(Settings)}, onNavigateToContactList = { onNavigate(ContactList)})
        Settings -> SettingsScreen(contactDao = contactDao, onNavigateToStart = { onNavigate(Start)})
        Chat -> ChatScreen(contactDao = contactDao, onNavigateToMain = { onNavigate(Main)})
        Contact-> ContactScreen(contactDao = contactDao, onNavigateToMain = { onNavigate(Main)})
        ContactList -> ContactListScreen(contactDao = contactDao, onNavigateToMain = { onNavigate(Main)}, onNavigateToSettings = { onNavigate(Settings)})
    }
}
