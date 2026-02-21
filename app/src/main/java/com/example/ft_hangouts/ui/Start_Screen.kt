package com.example.ft_hangouts.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.db.ContactDao

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
        ButtonStarttoMain(onNavigateToMain = onNavigateToMain)
        Spacer(modifier = Modifier.height(16.dp))
        ButtonStarttoContactList(onNavigateToContactList = onNavigateToContactList)
        Spacer(modifier = Modifier.height(16.dp))
        ButtonStarttoSettings(onNavigateToSettings = onNavigateToSettings)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ButtonStarttoMain(onNavigateToMain: () -> Unit) {
    Button(onClick = onNavigateToMain) {
        Text("To Main Screen")
    }
}

@Composable
fun ButtonStarttoContactList(onNavigateToContactList: () -> Unit) {
    Button(onClick = onNavigateToContactList) {
        Text("To Contact List Screen")
    }
}

@Composable
fun ButtonStarttoSettings(onNavigateToSettings: () -> Unit) {
    Button(onClick = onNavigateToSettings) {
        Text("To Settings Screen")
    }
}