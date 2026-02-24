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


//Buttons: -> ChatScreen, -> ContactListScreen, -> Settingsscreen, -> editContact, -> deleteContact

@Composable
fun ContactScreen(
    contact: Contact?,
    contactDao: ContactDao,
    onNavigateToContactList: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
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
        ButtonEditContact(contact = contact, contactDao = contactDao, onContactUpdated = {})
        Spacer(modifier = Modifier.height(16.dp))
        ButtonDeleteContact(contact = contact, contactDao = contactDao, onResult = { success -> showMessage = if (success) "success" else "failed" })
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
    var name by remember { mutableStateOf(contact?.name ?: "") }
    var phoneNumber by remember { mutableStateOf(contact?.phoneNumber ?: "") }
    var email by remember { mutableStateOf(contact?.email ?: "") }
    var address by remember { mutableStateOf(contact?.address ?: "") }
    var note by remember { mutableStateOf(contact?.note ?: "") }
    var pictureurl by remember { mutableStateOf(contact?.pictureurl ?: "") }

    Button(onClick = {
        if (contact != null) showDialog = true
    }) {
        Text("Edit Contact")
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Kontakt bearbeiten") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        label = { Text("Telefonnummer") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Adresse") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = note,
                        onValueChange = { note = it },
                        label = { Text("Notiz") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = pictureurl,
                        onValueChange = { pictureurl = it },
                        label = { Text("Bildurl (optional)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (contact != null && name.isNotBlank() && phoneNumber.isNotBlank()) {
                        try {
                            val updatedContact = contact.copy(
                                name = name,
                                phoneNumber = phoneNumber,
                                email = if (email.isBlank()) null else email,
                                address = if (address.isBlank()) null else address,
                                note = if (note.isBlank()) null else note,
                                pictureurl = if (note.isBlank()) null else pictureurl
                            )
                            contactDao.updateContact(updatedContact)
                            showDialog = false
                            onContactUpdated()
                        } catch (e: Exception) {
                        }
                    }
                }) {
                    Text("Speichern")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Abbrechen")
                }
            }
        )
    }
}