package com.example.ft_hangouts.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext

//Buttons: -> MainScreen, -> ContactScreen, -> Settingsscreen, -> CreateContact

@Composable
fun ContactListScreen(
    modifier: Modifier = Modifier,
    contactDao: ContactDao,
    onNavigateToMain: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToContact: (Contact) -> Unit)
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
        ButtonContactListtoMain(onNavigateToMain = onNavigateToMain)
        Spacer(modifier = Modifier.height(16.dp))
        ButtonContactListtoSettings(onNavigateToSettings = onNavigateToSettings)
        Spacer(modifier = Modifier.height(24.dp))
        ButtonCreateContact(contactDao = contactDao)
        Spacer(modifier = Modifier.height(24.dp))
        ContactList(contactDao = contactDao, onContactClick = onNavigateToContact)
        Spacer(modifier = Modifier.height(16.dp))
    }
}
@Composable
fun ContactList(
    modifier: Modifier = Modifier,
    contactDao: ContactDao,
    onContactClick: (Contact) -> Unit
) {
    val contacts by remember { mutableStateOf(contactDao.getAllContacts()) }
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
fun ButtonContactListtoMain(onNavigateToMain: () -> Unit) {
    Button(onClick = onNavigateToMain) {
        Text("To Main Screen")
    }
}
@Composable
fun ButtonContactListtoSettings(onNavigateToSettings: () -> Unit) {
    Button(onClick = onNavigateToSettings) {
        Text("To Settings Screen")
    }
}

@Composable
fun ButtonCreateContact(contactDao: ContactDao) {
    val context = LocalContext.current

    Button(onClick = {
        val newId = contactDao.insertContact(
            Contact(
                name = "New User",
                phoneNumber = "0123456789",
                email = "user@example.com",
                address = "Neue Straße 1",
                note = "New Contact",
                pictureurl = null
            )
        )
    }) {
        Text("Create Contact")
    }
}