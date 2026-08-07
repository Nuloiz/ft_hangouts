package com.example.ft_hangouts.ui.helpfunctions

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun ButtontoMain(onNavigateToMain: () -> Unit) {
    Button(onClick = onNavigateToMain) {
        Text("To Main Screen")
    }
}
@Composable
fun ButtontoSettings(onNavigateToSettings: () -> Unit) {
    Button(onClick = onNavigateToSettings) {
        Text("To Settings Screen")
    }
}

@Composable
fun ButtontoContactList(onNavigateToContactList: () -> Unit) {
    Button(onClick = onNavigateToContactList) {
        Text("To Contact List Screen")
    }
}