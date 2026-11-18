package com.example.ft_hangouts.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.coroutines.delay
import com.example.ft_hangouts.db.ContactDao
import com.example.ft_hangouts.db.Contact
import com.example.ft_hangouts.ui.helpfunctions.AppHeader
import com.example.ft_hangouts.ui.helpfunctions.ContactFormDialog
import com.example.ft_hangouts.ui.helpfunctions.ContactPicture
import com.example.ft_hangouts.ui.helpfunctions.ButtontoContactList
import com.example.ft_hangouts.ui.helpfunctions.ButtontoSettings
import com.example.ft_hangouts.ui.helpfunctions.PhoneCallButton
import com.example.ft_hangouts.ui.helpfunctions.Translations
import kotlinx.coroutines.delay

//Buttons: -> ChatScreen, -> ContactListScreen, -> Settingsscreen, -> editContact, -> deleteContact

@Composable
fun ContactScreen(
    contact: Contact?,
    contactDao: ContactDao,
    headerColor: Color,
    currentLanguage: String,
    onNavigateToContactList: () -> Unit,
    onNavigateToChat: (Contact) -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    var currentContact by remember(contact) { mutableStateOf(contact) }
    var showMessage by remember { mutableStateOf<String?>(null) }
    val scrollState = rememberScrollState()
    LaunchedEffect(showMessage) {
        when (showMessage) {
            "success" -> {
                Toast.makeText(context, Translations.tr("del_success", currentLanguage), Toast.LENGTH_SHORT).show()
                delay(2000)
                onNavigateToContactList()
                showMessage = null
            }
            "failed" -> {
                Toast.makeText(context, Translations.tr("del_failed", currentLanguage), Toast.LENGTH_SHORT).show()
                delay(2000)
                showMessage = null
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .verticalScroll(scrollState)
    ) {
        if (currentContact != null) {
            ContactHeader(
                contact = currentContact!!,
                headerColor = headerColor
            )
        } else {
            AppHeader(
                title = "Error",
                headerColor = headerColor
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val activeContact = currentContact
            if (activeContact != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = Translations.tr("phone", currentLanguage) + activeContact.phoneNumber,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(1f)
                    )
                    PhoneCallButton(
                        phoneNumber = activeContact.phoneNumber,
                        modifier = Modifier.size(48.dp)
                    )
                }
                if (!activeContact.email.isNullOrBlank()) {
                    Text(
                        text = "E-Mail: ${activeContact.email}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                if (!activeContact.address.isNullOrBlank()) {
                    Text(
                        text = Translations.tr("address", currentLanguage) + activeContact.address,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                if (!activeContact.note.isNullOrBlank()) {
                    Text(
                        text = Translations.tr("note", currentLanguage) + activeContact.note,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                if (isLandscape) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { onNavigateToChat(activeContact) },
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = Translations.tr("open_chat", currentLanguage),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Button(
                            onClick = onNavigateToContactList,
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = Translations.tr("contacts", currentLanguage),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Button(
                            onClick = onNavigateToSettings,
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = Translations.tr("settings", currentLanguage),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        ButtonEditContact(
                            modifier = Modifier.weight(1f),
                            contact = activeContact,
                            contactDao = contactDao,
                            currentLanguage = currentLanguage,
                            onContactUpdated = { updated -> currentContact = updated }
                        )
                        ButtonDeleteContact(
                            modifier = Modifier.weight(1f),
                            contact = activeContact,
                            contactDao = contactDao,
                            currentLanguage = currentLanguage,
                            onResult = { success -> showMessage = if (success) "success" else "failed" }
                        )
                    }
                } else {
                    Button(
                        onClick = { onNavigateToChat(activeContact) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(Translations.tr("open_chat", currentLanguage))
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = onNavigateToContactList,
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = Translations.tr("contacts", currentLanguage),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Button(
                            onClick = onNavigateToSettings,
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = Translations.tr("settings", currentLanguage),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ButtonEditContact(
                            modifier = Modifier.weight(1f),
                            contact = activeContact,
                            contactDao = contactDao,
                            currentLanguage = currentLanguage,
                            onContactUpdated = { updated -> currentContact = updated }
                        )
                        ButtonDeleteContact(
                            modifier = Modifier.weight(1f),
                            contact = activeContact,
                            contactDao = contactDao,
                            currentLanguage = currentLanguage,
                            onResult = { success -> showMessage = if (success) "success" else "failed" }
                        )
                    }
                }
            } else {
                Text("Error: Contact not found")
            }
        }
    }
}

@Composable
fun ContactHeader(
    contact: Contact,
    headerColor: Color
) {
    val textColor = if (headerColor.luminance() < 0.5f) Color.White else Color.Black
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(headerColor)
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(64.dp)) {
            ContactPicture(contact = contact)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = contact.name,
            style = MaterialTheme.typography.headlineMedium,
            color = textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ButtonDeleteContact(
    modifier: Modifier = Modifier,
    contact: Contact?,
    contactDao: ContactDao,
    currentLanguage: String,
    onResult: (Boolean) -> Unit
) {
    Button(
        onClick = {
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
        },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Text(
            text = Translations.tr("delete_contact", currentLanguage),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun ButtonEditContact(
    modifier: Modifier = Modifier,
    contact: Contact?,
    contactDao: ContactDao,
    currentLanguage: String,
    onContactUpdated: (Contact) -> Unit
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    Button(
        onClick = { if (contact != null) showDialog = true },
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Text(
            text = Translations.tr("edit_contact", currentLanguage),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
    if (showDialog) {
        ContactFormDialog(
            isCreateMode = false,
            currentLanguage = currentLanguage,
            initialContact = contact,
            onDismiss = { showDialog = false },
            onSave = { updatedContact ->
                contactDao.updateContact(updatedContact)
                showDialog = false
                onContactUpdated(updatedContact)
            }
        )
    }
}