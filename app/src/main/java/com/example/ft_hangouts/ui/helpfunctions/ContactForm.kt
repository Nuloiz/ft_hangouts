package com.example.ft_hangouts.ui.helpfunctions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.example.ft_hangouts.db.Contact
import android.content.res.Configuration
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.window.DialogProperties

@Composable
fun ContactFormDialog(
    isCreateMode: Boolean,
    currentLanguage: String,
    initialContact: Contact? = null,
    onDismiss: () -> Unit,
    onSave: (Contact) -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    var name by rememberSaveable { mutableStateOf(initialContact?.name ?: "") }
    var phoneNumber by rememberSaveable { mutableStateOf(initialContact?.phoneNumber ?: "+") }
    var email by rememberSaveable { mutableStateOf(initialContact?.email ?: "") }
    var address by rememberSaveable { mutableStateOf(initialContact?.address ?: "") }
    var note by rememberSaveable { mutableStateOf(initialContact?.note ?: "") }
    var pictureUrl by rememberSaveable { mutableStateOf(initialContact?.pictureurl ?: "") }
    var validation by remember { mutableStateOf(ContactValidationResult()) }
    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        pictureUrl = uri?.toString() ?: ""
    }
    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
            .fillMaxWidth(if (isLandscape) 0.85f else 0.92f)
            .imePadding(),
        title = {
            Text(
                if (isCreateMode) Translations.tr("create_contact", currentLanguage)
                else Translations.tr("edit_con", currentLanguage)
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (isLandscape) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            ContactTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = "Name",
                                error = validation.nameError,
                                maxLength = 20,
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                            )
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            ContactTextField(
                                value = phoneNumber,
                                onValueChange = { input ->
                                    var new = input
                                    if (!new.startsWith("+")) new = "+${new.trimStart('+')}"
                                    val digits = new.drop(1).filter { it.isDigit() }
                                    val combined = "+" + digits
                                    if (combined.length <= 20) phoneNumber = combined
                                },
                                label = Translations.tr("phone_number", currentLanguage) + " (opt.)",
                                error = validation.phoneError,
                                maxLength = 20,
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            ContactTextField(
                                value = email,
                                onValueChange = { input ->
                                    if (input.length <= 50 && !input.contains(' ') && !input.contains('\n')) {
                                        email = input
                                    }
                                },
                                label = "E-mail (opt.)",
                                error = validation.emailError,
                                maxLength = 50,
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
                            )
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            ContactTextField(
                                value = address,
                                onValueChange = { if (it.length <= 50) address = it },
                                label = Translations.tr("address", currentLanguage) + " (opt.)",
                                error = validation.addressError,
                                maxLength = 50,
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                            )
                        }
                    }
                } else {
                    ContactTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Name",
                        error = validation.nameError,
                        maxLength = 20,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                    )
                    ContactTextField(
                        value = phoneNumber,
                        onValueChange = { input ->
                            var new = input
                            if (!new.startsWith("+")) new = "+${new.trimStart('+')}"
                            val digits = new.drop(1).filter { it.isDigit() }
                            val combined = "+" + digits
                            if (combined.length <= 20) phoneNumber = combined
                        },
                        label = Translations.tr("phone_number", currentLanguage) + "(optional)",
                        error = validation.phoneError,
                        maxLength = 20,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
                    )
                    ContactTextField(
                        value = email,
                        onValueChange = { input ->
                            if (input.length <= 50 && !input.contains(' ') && !input.contains('\n')) {
                                email = input
                            }
                        },
                        label = "E-mail: (optional)",
                        error = validation.emailError,
                        maxLength = 50,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
                    )
                    ContactTextField(
                        value = address,
                        onValueChange = { if (it.length <= 50) address = it },
                        label = Translations.tr("address", currentLanguage) + "(optional)",
                        error = validation.addressError,
                        maxLength = 50,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                    )
                }
                ContactTextField(
                    value = note,
                    onValueChange = { if (it.length <= 500) note = it },
                    label = Translations.tr("note", currentLanguage) + "(optional)",
                    error = validation.noteError,
                    maxLength = 500,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                )
                GalleryImagePickerField(
                    launcher = imageLauncher,
                    pictureUrl = pictureUrl,
                    currentLanguage = currentLanguage,
                    onPictureUrlChanged = { newUrl: String? -> pictureUrl = newUrl ?: "" }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val result = validateContactInput(
                        currentLanguage = currentLanguage,
                        name = name,
                        phone = phoneNumber,
                        email = email,
                        address = address,
                        note = note,
                        pictureUrl = pictureUrl
                    )
                    validation = result
                    if (result.isValid) {
                        val contact = Contact(
                            id = initialContact?.id ?: 0L,
                            name = name,
                            phoneNumber = phoneNumber,
                            email = if (email.isBlank()) null else email,
                            address = if (address.isBlank()) null else address,
                            note = if (note.isBlank()) null else note,
                            pictureurl = if (pictureUrl.isBlank()) null else pictureUrl
                        )
                        onSave(contact)
                    }
                }
            ) {
                Text(
                    if (isCreateMode) Translations.tr("create", currentLanguage)
                    else Translations.tr("save", currentLanguage)
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(Translations.tr("cancel", currentLanguage))
            }
        }
    )
}

@Composable
private fun ContactTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    error: String?,
    maxLength: Int,
    keyboardOptions: KeyboardOptions
) {
    OutlinedTextField(
        value = value,
        onValueChange = { if (it.length <= maxLength) onValueChange(it) },
        label = { Text(label) },
        isError = error != null,
        supportingText = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                if (error != null) {
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
                Text(text = "${value.length}/$maxLength")
            }
        },
        keyboardOptions = keyboardOptions,
        modifier = Modifier.fillMaxWidth()
    )
}