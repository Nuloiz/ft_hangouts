package com.example.ft_hangouts.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import com.example.ft_hangouts.db.ContactDao
import com.example.ft_hangouts.db.Contact
import com.example.ft_hangouts.ui.helpfunctions.ContactFormDialog
import com.example.ft_hangouts.ui.helpfunctions.ContactPicture

//Buttons: -> ChatScreen, -> ContactListScreen, -> Settingsscreen, -> editContact, -> deleteContact

@Composable
fun ContactScreen(
    contact: Contact?,
    contactDao: ContactDao,
    onNavigateToContactList: () -> Unit,
    onNavigateToChat: (Contact) -> Unit,
    onNavigateToSettings: () -> Unit
) {
    //var contact by remember { mutableStateOf(contactDao.getContactById(contact?.id)) }
    //fun reloadScreen() {contact = contactDao.getContactById(contact?.id)}
    val context = LocalContext.current
    var showMessage by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(showMessage) {
        when (showMessage) {
            "success" -> {
                Toast.makeText(context, "Deletion successful", Toast.LENGTH_SHORT).show()
                delay(2000)
                onNavigateToContactList()
                showMessage = null
            }
            "failed" -> {
                Toast.makeText(context, "Deletion failed", Toast.LENGTH_SHORT).show()
                delay(2000)
                showMessage = null
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        if (contact != null) {
            ContactPicture(contact)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Contact: ${contact.name}", style = MaterialTheme.typography.headlineMedium)
            Text("Phone: ${contact.phoneNumber}")
            if (!contact.email.isNullOrBlank()) {
                Text("Email: ${contact.email}")
            }
            if (!contact.address.isNullOrBlank()) {
                Text("Address: ${contact.address}")
            }
            if (!contact.note.isNullOrBlank()) {
                Text("Note: ${contact.note}")
            }
            if (!contact.pictureurl.isNullOrBlank()) {
                Text("Picture: ${contact.pictureurl}")
            }
            Spacer(modifier = Modifier.height(16.dp))
        } else {
            Text("Error: Contact not found")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigateToContactList) { Text("Zurück zur Liste") }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigateToSettings) { Text("to Settings") }
        Spacer(modifier = Modifier.height(16.dp))
        ButtonEditContact(contact = contact, contactDao = contactDao, onContactUpdated = {/*reloadScreen()*/})
        Spacer(modifier = Modifier.height(16.dp))
        ButtonDeleteContact(contact = contact, contactDao = contactDao, onResult = { success -> showMessage = if (success) "success" else "failed" })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onNavigateToChat(contact!!) }) { Text("Chat öffnen") } //das doppelte Ausrufezeichen überprüfen
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ButtonDeleteContact(
    contact: Contact?,
    contactDao: ContactDao,
    onResult: (Boolean) -> Unit
) {
    Button(onClick = {
        if (contact != null) {
            try {
                contactDao.deleteContact(contact.id)
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        } else {
            onResult(false)
        }
    }) {
        Text("Delete Contact")
    }
}

@Composable
fun ButtonEditContact(
    contact: Contact?,
    contactDao: ContactDao,
    onContactUpdated: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    Button(onClick = { if (contact != null) showDialog = true }) {
        Text("Edit Contact")
    }
    if (showDialog) {
        ContactFormDialog(
            isCreateMode = false,
            initialContact = contact,
            onDismiss = { showDialog = false },
            onSave = { updatedContact ->
                contactDao.updateContact(updatedContact)
                showDialog = false
                onContactUpdated()
            }
        )
    }
}