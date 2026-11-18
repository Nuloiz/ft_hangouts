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
import android.content.res.Configuration
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import com.example.ft_hangouts.db.Contact
import com.example.ft_hangouts.db.ContactDao
import com.example.ft_hangouts.ui.helpfunctions.AppHeader
import com.example.ft_hangouts.ui.helpfunctions.ButtontoMain
import com.example.ft_hangouts.ui.helpfunctions.ButtontoSettings
import com.example.ft_hangouts.ui.helpfunctions.ContactFormDialog
import com.example.ft_hangouts.ui.helpfunctions.Translations

//Buttons: -> MainScreen, -> ContactScreen, -> Settingsscreen, -> CreateContact

@Composable
fun ContactListScreen(
    modifier: Modifier = Modifier,
    contactDao: ContactDao,
    headerColor: Color,
    currentLanguage: String,
    onNavigateToMain: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToContact: (Contact) -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    var contacts by remember { mutableStateOf(contactDao.getAllContacts()) }
    fun reloadScreen() { contacts = contactDao.getAllContacts() }
    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        AppHeader(
            title = Translations.tr("contacts", currentLanguage),
            headerColor = headerColor
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (isLandscape) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = onNavigateToMain,
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = Translations.tr("to_main_screen", currentLanguage),
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
                    ButtonCreateContact(
                        modifier = Modifier.weight(1f),
                        contactDao = contactDao,
                        currentLanguage = currentLanguage,
                        onContactCreated = { reloadScreen() }
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = onNavigateToMain,
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = Translations.tr("to_main_screen", currentLanguage),
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
                Spacer(modifier = Modifier.height(8.dp))
                ButtonCreateContact(
                    modifier = Modifier.fillMaxWidth(),
                    contactDao = contactDao,
                    currentLanguage = currentLanguage,
                    onContactCreated = { reloadScreen() }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            ContactList(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contacts = contacts,
                onContactClick = onNavigateToContact
            )
        }
    }
}

@Composable
fun ButtonCreateContact(
    modifier: Modifier = Modifier,
    contactDao: ContactDao,
    currentLanguage: String,
    onContactCreated: () -> Unit
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    Button(
        onClick = { showDialog = true },
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Text(
            text = Translations.tr("create_contact", currentLanguage),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
    if (showDialog) {
        ContactFormDialog(
            isCreateMode = true,
            currentLanguage = currentLanguage,
            onDismiss = { showDialog = false },
            onSave = { newContact ->
                contactDao.insertContact(newContact)
                showDialog = false
                onContactCreated()
            }
        )
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
