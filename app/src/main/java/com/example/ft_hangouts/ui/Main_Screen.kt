package com.example.ft_hangouts.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.db.ContactDao
import com.example.ft_hangouts.ui.helpfunctions.*

//Buttons: -> ChatScreen, -> ContactListScreen, -> Settingsscreen, -> NewChat (-> ExistingUser, -> NewUser), -> AktualisierenMain

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    contactDao: ContactDao,
    onNavigateToSettings: () -> Unit,
    onNavigateToContactList: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Main Screen",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(24.dp))
        ButtontoSettings(onNavigateToSettings = onNavigateToSettings)
        Spacer(modifier = Modifier.height(16.dp))
        ButtontoContactList(onNavigateToContactList = onNavigateToContactList)
        Spacer(modifier = Modifier.height(16.dp))
    }
}
