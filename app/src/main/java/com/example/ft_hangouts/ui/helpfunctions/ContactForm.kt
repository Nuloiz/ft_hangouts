package com.example.ft_hangouts.ui.helpfunctions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.db.Contact

@Composable
fun ContactFormDialog(
    isCreateMode: Boolean,
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

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                if (isCreateMode) "Neuen Kontakt erstellen"
                else "Kontakt bearbeiten"
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
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
                    label = "Telefonnummer",
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
                    label = "Email (optional)",
                    error = validation.emailError,
                    maxLength = 50,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
                )

                ContactTextField(
                    value = address,
                    onValueChange = { if (it.length <= 50) address = it },
                    label = "Adresse (optional)",
                    error = validation.addressError,
                    maxLength = 50,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                )

                ContactTextField(
                    value = note,
                    onValueChange = { if (it.length <= 500) note = it },
                    label = "Notiz (optional)",
                    error = validation.noteError,
                    maxLength = 500,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                )

                ContactTextField(
                    value = pictureUrl,
                    onValueChange = { if (it.length <= 100) pictureUrl = it },
                    label = "Bild-URL (optional)",
                    error = validation.pictureUrlError,
                    maxLength = 100,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Uri)
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
                Text(if (isCreateMode) "Erstellen" else "Speichern")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Abbrechen")
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
    keyboardOptions: androidx.compose.ui.text.input.KeyboardOptions
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
