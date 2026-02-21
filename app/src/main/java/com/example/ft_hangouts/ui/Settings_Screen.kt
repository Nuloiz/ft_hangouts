package com.example.ft_hangouts.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.db.ContactDao

//Buttons: -> StartScreen, -> ChangeTheme, -> ChangeLanguage, -> ChangeTime

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    contactDao: ContactDao,
    onNavigateToStart: () -> Unit)
{
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Settings Screen",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(24.dp))
        ButtonSettingstoStart(onNavigateToStart = onNavigateToStart)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ButtonSettingstoStart(onNavigateToStart: () -> Unit) {
    Button(onClick = onNavigateToStart) {
        Text("To Start Screen")
    }
}