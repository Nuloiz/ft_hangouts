package com.example.ft_hangouts.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.db.Contact
import com.example.ft_hangouts.db.ContactDao
import com.example.ft_hangouts.ui.helpfunctions.*

//Buttons: -> ChatScreen, -> ContactListScreen, -> Settingsscreen, -> NewChat (-> ExistingUser, -> NewUser), -> AktualisierenMain

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    contactDao: ContactDao,
    onNavigateToSettings: () -> Unit,
    onNavigateToContactList: () -> Unit,
    onNavigateToChat: (Contact) -> Unit
) {
    var contacts by remember { mutableStateOf(contactDao.getAllContacts()) }
    fun reloadScreen() {contacts = contactDao.getAllContacts()}
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Main Screen",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(24.dp))
        ButtontoSettings(onNavigateToSettings = onNavigateToSettings, "Settings")
        Spacer(modifier = Modifier.height(16.dp))
        ButtontoContactList(onNavigateToContactList = onNavigateToContactList, "Contacts")
        Spacer(modifier = Modifier.height(16.dp))
        ChatList(contacts = contacts, onContactClick = onNavigateToChat)
    }
}

@Composable
fun ChatList(
    modifier: Modifier = Modifier,
    contacts: List<Contact>,
    onContactClick: (Contact) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(contacts) { contact ->
            ChatListItem(contact = contact, onClick = { onContactClick(contact) }
            )
        }
    }
}

@Composable
fun ChatListItem(
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
