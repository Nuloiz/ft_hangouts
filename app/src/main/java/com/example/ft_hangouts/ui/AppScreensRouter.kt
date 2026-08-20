package com.example.ft_hangouts.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.ft_hangouts.db.ContactDao
import com.example.ft_hangouts.db.MessageDao
import com.example.ft_hangouts.ui.AppScreens.*
import com.example.ft_hangouts.ui.helpfunctions.HeaderColorManager
import com.example.ft_hangouts.ui.helpfunctions.LanguageManager

@Composable
fun AppScreenRouter(
    currentScreen: AppScreens,
    onNavigate: (AppScreens) -> Unit,
    contactDao: ContactDao,
    messageDao: MessageDao)
{
    val context = LocalContext.current
    var headerColor by remember {
        mutableStateOf(HeaderColorManager.getSavedColor(context)) }
    var currentLanguage by remember {
        mutableStateOf(LanguageManager.getSavedLanguage(context)) }
    when (currentScreen)
    {
        Start -> StartScreen(contactDao = contactDao, headerColor = headerColor, currentLanguage = currentLanguage, onNavigateToMain = { onNavigate(Main)}, onNavigateToContactList = { onNavigate(ContactList)}, onNavigateToSettings = { onNavigate(Settings)})
        Main -> MainScreen(contactDao = contactDao, messageDao = messageDao, headerColor = headerColor, currentLanguage = currentLanguage, onNavigateToSettings = { onNavigate(Settings)}, onNavigateToContactList = { onNavigate(ContactList)}, onNavigateToChat = {selectedContact -> onNavigate(Chat(selectedContact))})
        Settings -> SettingsScreen(headerColor = headerColor, currentLanguage = currentLanguage, onNavigateToStart = { onNavigate(Start)}, currentHeaderColor = headerColor, onHeaderColorChanged = { newColor -> headerColor = newColor }, onLanguageChanged = { newLang -> currentLanguage = newLang }, onNavigateToMain = { onNavigate(Main)})
        ContactList -> ContactListScreen(contactDao = contactDao, headerColor = headerColor, currentLanguage = currentLanguage, onNavigateToMain = { onNavigate(Main)}, onNavigateToSettings = { onNavigate(Settings)}, onNavigateToContact = {selectedContact -> onNavigate(Contact(selectedContact))})
        is Contact -> ContactScreen(contact = currentScreen.selectedContact, contactDao = contactDao, currentLanguage = currentLanguage, onNavigateToContactList = { onNavigate(ContactList) }, onNavigateToChat = {selectedContact -> onNavigate(Chat(selectedContact))}, onNavigateToSettings = { onNavigate(Settings) })
        is Chat -> ChatScreen(contact = currentScreen.selectedContact, contactDao = contactDao, messageDao = messageDao, headerColor = headerColor, currentLanguage = currentLanguage, onNavigateToMain = { onNavigate(Main)}, onNavigateToContact = { selectedContact -> onNavigate(Contact(selectedContact))}, onNavigateToSettings = { onNavigate(Settings) })
    }
}
