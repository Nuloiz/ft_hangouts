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
        Start -> StartScreen(contactDao = contactDao, onNavigateToMain = { onNavigate(Main)})
        Main -> MainScreen(contactDao = contactDao, onNavigateToMain = { onNavigate(Main)})
        //Settings -> SettingsScreen(contactDao = contactDao)
        //Chat -> ChatScreen(contactDao = contactDao)
        //Contact-> ContactScreen(contactDao = contactDao)
        //ContactList -> ContactListScreen(contactDao = contactDao)
    }
}
