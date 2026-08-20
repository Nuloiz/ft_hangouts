package com.example.ft_hangouts.ui

import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color
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
    headerColor: Color,
    onNavigateToMain: () -> Unit,
    onNavigateToContactList: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()) {
        AppHeader(title = "ft_hangouts", headerColor = headerColor)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
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
}

