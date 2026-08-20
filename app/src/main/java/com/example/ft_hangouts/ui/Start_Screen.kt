package com.example.ft_hangouts.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.db.ContactDao
import com.example.ft_hangouts.ui.helpfunctions.*
//Buttons: -> MainScreen, -> ContactListScreen, -> Settingsscreen

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    contactDao: ContactDao,
    onNavigateToMain: () -> Unit,
    onNavigateToContactList: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "ft_hangouts",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Start_Screen",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(24.dp))
        Testbuttondb(contactDao = contactDao)
        Spacer(modifier = Modifier.height(16.dp))
        TestButtonContactList(contactDao = contactDao)
        Spacer(modifier = Modifier.height(16.dp))
        ButtontoMain(onNavigateToMain = onNavigateToMain, "To Main Screen")
        Spacer(modifier = Modifier.height(16.dp))
        ButtontoContactList(onNavigateToContactList = onNavigateToContactList, "Contacts")
        Spacer(modifier = Modifier.height(16.dp))
        ButtontoSettings(onNavigateToSettings = onNavigateToSettings, "Settings")
        Spacer(modifier = Modifier.height(16.dp))
    }
}

