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

@Composable
fun ContactFormDialog(
    isCreateMode: Boolean,
    currentLanguage: String,
    initialContact: Contact? = null,
    onDismiss: () -> Unit,
    onSave: (Contact) -> Unit
) {
    var name by remember { mutableStateOf(initialContact?.name ?: "") }
    var phoneNumber by remember { mutableStateOf(initialContact?.phoneNumber ?: "+") }
    var email by remember { mutableStateOf(initialContact?.email ?: "") }
    var address by remember { mutableStateOf(initialContact?.address ?: "") }
    var note by remember { mutableStateOf(initialContact?.note ?: "") }
    var pictureUrl by remember { mutableStateOf(initialContact?.pictureurl ?: "") }
    var validation by remember { mutableStateOf(ContactValidationResult()) }

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        pictureUrl = uri?.toString() ?: ""
    }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                if (isCreateMode) Translations.tr(("create_contact"), currentLanguage)
                else Translations.tr(("edit_con"), currentLanguage)
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .imePadding()
                ) {
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
                    label = Translations.tr(("phone_number"), currentLanguage) + "(optional)",
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
                    label = Translations.tr(("adress"), currentLanguage) + "(optional)",
                    error = validation.addressError,
                    maxLength = 50,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                )

                ContactTextField(
                    value = note,
                    onValueChange = { if (it.length <= 500) note = it },
                    label = Translations.tr(("note"), currentLanguage) + "(optional)",
                    error = validation.noteError,
                    maxLength = 500,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                )

                GalleryImagePickerField(
                    launcher = imageLauncher,
                    pictureUrl = pictureUrl,
                    onPictureUrlChanged = { newUrl: String? -> pictureUrl = newUrl ?: "" }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val result = validateContactInput(
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
                Text(if (isCreateMode) Translations.tr(("create"), currentLanguage) else Translations.tr(("save"), currentLanguage))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(Translations.tr(("cancel"), currentLanguage))
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
            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            Text("${value.length}/$maxLength")
        },
        keyboardOptions = keyboardOptions,
        modifier = Modifier.fillMaxWidth()
    )
}