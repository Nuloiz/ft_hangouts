package com.example.ft_hangouts.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.db.ContactDao
import com.example.ft_hangouts.db.Contact


//Buttons: -> ChatScreen, -> ContactListScreen, -> Settingsscreen, -> editContact, -> deleteContact

@Composable
fun ContactScreen(
    contact: Contact?,
    contactDao: ContactDao,
    onNavigateToContactList: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        if (contact != null) {
            Text("Contact: ${contact.name}", style = MaterialTheme.typography.headlineMedium)
            Text("Phone: ${contact.phoneNumber}")
            if (!contact.email.isNullOrBlank()) {
                Text("Email: ${contact.email}")
            }
            Spacer(modifier = Modifier.height(16.dp))
        } else {
            // Edge Case redirect to ContactListScreen
            Text("Allgemeiner Contact‑Übersicht")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigateToContactList) { Text("Zurück zur Liste") }
    }
}