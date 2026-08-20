package com.example.ft_hangouts.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.ft_hangouts.db.Contact
import com.example.ft_hangouts.db.ContactDao
import com.example.ft_hangouts.ui.helpfunctions.ContactFormDialog
import com.example.ft_hangouts.ui.helpfunctions.*

//Buttons: -> MainScreen, -> ContactScreen, -> Settingsscreen, -> CreateContact

@Composable
fun ContactListScreen(
    modifier: Modifier = Modifier,
    contactDao: ContactDao,
    headerColor: Color,
    onNavigateToMain: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToContact: (Contact) -> Unit)
{
    var contacts by remember { mutableStateOf(contactDao.getAllContacts()) }
    fun reloadScreen() {contacts = contactDao.getAllContacts()}
    Column(
        modifier = modifier.fillMaxSize()) {
        AppHeader(title = "Contacts", headerColor = headerColor)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ButtontoMain(onNavigateToMain = onNavigateToMain, "To Main Screen")
            Spacer(modifier = Modifier.height(24.dp))
            ButtontoSettings(onNavigateToSettings = onNavigateToSettings, "Settings")
            Spacer(modifier = Modifier.height(24.dp))
            ButtonCreateContact(contactDao = contactDao, onContactCreated = { reloadScreen() })
            Spacer(modifier = Modifier.height(24.dp))
            ContactList(contacts = contacts, onContactClick = onNavigateToContact)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
@Composable
fun ContactList(
    modifier: Modifier = Modifier,
    contacts: List<Contact>,
    onContactClick: (Contact) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(contacts) { contact ->
            ContactListItem(contact = contact, onClick = { onContactClick(contact) }
            )
        }
    }
}

@Composable
fun ContactListItem(
    contact: Contact,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
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

@Composable
fun ButtonCreateContact(
    contactDao: ContactDao,
    onContactCreated: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    Button(onClick = { showDialog = true }) {
        Text("Create Contact")
    }
    if (showDialog) {
        ContactFormDialog(
            isCreateMode = true,
            onDismiss = { showDialog = false },
            onSave = { newContact ->
                contactDao.insertContact(newContact)
                showDialog = false
                onContactCreated()
            }
        )
    }
}