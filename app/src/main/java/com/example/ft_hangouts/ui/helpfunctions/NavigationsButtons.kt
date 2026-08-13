package com.example.ft_hangouts.ui.helpfunctions

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun ButtontoMain(onNavigateToMain: () -> Unit, message: String = "To Main Screen") {
    Button(onClick = onNavigateToMain) {
        Text(message)
    }
}
@Composable
fun ButtontoSettings(onNavigateToSettings: () -> Unit, message: String = "To Settings Screen") {
    Button(onClick = onNavigateToSettings) {
        Text(message)
    }
}

@Composable
fun ButtontoContactList(onNavigateToContactList: () -> Unit, message: String = "To Contact List Screen") {
    Button(onClick = onNavigateToContactList) {
        Text(message)
    }
}