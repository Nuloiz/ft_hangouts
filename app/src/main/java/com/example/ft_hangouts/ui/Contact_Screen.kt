package com.example.ft_hangouts.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    var showMessage by remember { mutableStateOf<String?>(null) }
    val scrollState = rememberScrollState()
    LaunchedEffect(showMessage) {
        when (showMessage) {
            "success" -> {
                Toast.makeText(context, Translations.tr(("del_success"), currentLanguage), Toast.LENGTH_SHORT).show()
                delay(2000)
                onNavigateToContactList()
                showMessage = null
            }
            "failed" -> {
                Toast.makeText(context, Translations.tr(("del_failed"), currentLanguage), Toast.LENGTH_SHORT).show()
                delay(2000)
                showMessage = null
            }
        }
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .navigationBarsPadding()
        .verticalScroll(scrollState)) {
        if (contact != null) {
            AppHeader(
                title = contact.name,
                headerColor = headerColor
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (contact != null) {
            ContactPicture(contact)
            Spacer(modifier = Modifier.height(16.dp))
            Text(Translations.tr(("phone"), currentLanguage) + contact.phoneNumber)
            if (!contact.email.isNullOrBlank()) {
                Text("E-Mail: ${contact.email}")
            }
            if (!contact.address.isNullOrBlank()) {
                Text(Translations.tr(("address"), currentLanguage) + contact.address)
            }
            if (!contact.note.isNullOrBlank()) {
                Text(Translations.tr(("note"), currentLanguage) + contact.note)
            }
            if (!contact.pictureurl.isNullOrBlank()) {
                Text(Translations.tr(("picture"), currentLanguage) + contact.pictureurl)
            }
            Spacer(modifier = Modifier.height(16.dp))
            } else {
                Text("Error: Contact not found")
            }
            if (contact != null) {
                PhoneCallButton(
                    phoneNumber = contact.phoneNumber,
                    modifier = Modifier.size(56.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            ButtontoContactList(
                onNavigateToContactList = onNavigateToContactList,
                Translations.tr(("contacts"), currentLanguage)
            )
            Spacer(modifier = Modifier.height(16.dp))
            ButtontoSettings(
                onNavigateToSettings = onNavigateToSettings,
                Translations.tr(("settings"), currentLanguage)
            )
            Spacer(modifier = Modifier.height(16.dp))
            ButtonEditContact(
                contact = contact,
                contactDao = contactDao,
                currentLanguage = currentLanguage,
                onContactUpdated = {/*reloadScreen()*/ })
            Spacer(modifier = Modifier.height(16.dp))
            ButtonDeleteContact(
                contact = contact,
                contactDao = contactDao,
                currentLanguage = currentLanguage,
                onResult = { success -> showMessage = if (success) "success" else "failed" })
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onNavigateToChat(contact!!) }) {
                Text(
                    Translations.tr(
                        ("open_chat"),
                        currentLanguage
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ButtonDeleteContact(
    contact: Contact?,
    contactDao: ContactDao,
    currentLanguage: String,
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
        Text(Translations.tr(("delete_contact"), currentLanguage))
    }
}

@Composable
fun ButtonEditContact(
    contact: Contact?,
    contactDao: ContactDao,
    currentLanguage: String,
    onContactUpdated: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    Button(onClick = { if (contact != null) showDialog = true }) {
        Text(Translations.tr(("edit_contact"), currentLanguage))
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
                onContactUpdated()
            }
        )
    }
}