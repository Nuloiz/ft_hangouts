package com.example.ft_hangouts.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.db.Contact
import com.example.ft_hangouts.db.ContactDao
import androidx.compose.runtime.Composable

//Buttons: -> MainScreen, -> ContactScreen, -> Settingsscreen, -> CreateContact

@Composable
fun ContactListScreen(
    modifier: Modifier = Modifier,
    contactDao: ContactDao,
    onNavigateToMain: () -> Unit)
{
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Contact List Screen",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}
@Composable
fun ContactList(
    modifier: Modifier = Modifier,
    contactDao: ContactDao
) {
    val contacts by remember { mutableStateOf(contactDao.getAllContacts()) }
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(contacts) { contact ->
            ContactListItem(contact = contact)
        }
    }
}

@Composable
fun ContactListItem(contact: Contact) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = contact.name,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = contact.phoneNumber,
                style = MaterialTheme.typography.bodyMedium
            )
            if (!contact.email.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = contact.email!!,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
