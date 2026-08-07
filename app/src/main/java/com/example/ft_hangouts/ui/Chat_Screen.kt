package com.example.ft_hangouts.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.db.ContactDao
import com.example.ft_hangouts.db.MessageDao

//Buttons: -> MainScreen, -> ContactScreen, -> Settingsscreen, -> AktualisierenChat, -> SendMessage
//Schreibfeld -> WriteMessage

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    contact: com.example.ft_hangouts.db.Contact,
    contactDao: ContactDao,
    messageDao: MessageDao,
    onNavigateToMain: () -> Unit,
    onNavigateToContact: () -> Unit,
    onNavigateToSettings: () -> Unit)
{
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Chat Screen",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}