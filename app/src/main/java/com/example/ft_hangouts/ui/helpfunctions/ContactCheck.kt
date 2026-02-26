package com.example.ft_hangouts.ui.helpfunctions

data class ContactValidationResult(
    val nameError: String? = null,
    val phoneError: String? = null,
    val emailError: String? = null,
    val addressError: String? = null,
    val noteError: String? = null,
    val pictureUrlError: String? = null
) {
    val isValid: Boolean
        get() = listOf(
            nameError, phoneError, emailError,
            addressError, noteError, pictureUrlError
        ).all { it == null }
}

fun containsForbiddenSqlChars(value: String): Boolean {
    val forbidden = listOf(";", "--", "/*", "*/", "'", "\"", "DROP", "SELECT", "INSERT")
    return forbidden.any { value.contains(it, ignoreCase = true) }
}

fun validateContactInput(
    name: String,
    phone: String,
    email: String,
    address: String,
    note: String,
    pictureUrl: String
): ContactValidationResult {
    var nameError: String? = null
    var phoneError: String? = null
    var emailError: String? = null
    var addressError: String? = null
    var noteError: String? = null
    var pictureUrlError: String? = null

    if (name.isBlank()) {
        nameError = "Name darf nicht leer sein."
    } else if (name.length > 20) {
        nameError = "Name maximal 20 Zeichen."
    } else if (containsForbiddenSqlChars(name)) {
        nameError = "Ungültige Zeichen im Namen."
    }

    if (phone.isBlank()) {
        phoneError = "Telefonnummer erforderlich."
    } else if (!phone.startsWith("+")) {
        phoneError = "Muss mit + beginnen."
    } else {
        val digits = phone.drop(1)
        if (!digits.all { it.isDigit() }) {
            phoneError = "Nur Ziffern nach + erlaubt."
        }
    }
    if (phone.length < 5 || phone.length > 20) {
        phoneError = "Telefonnummer 5–20 Zeichen."
    }

    if (email.isNotBlank()) {
        if (email.length > 50) {
            emailError = "Email maximal 50 Zeichen."
        } else if (' ' in email || '\n' in email) {
            emailError = "Keine Leerzeichen in Email."
        } else if (!email.contains("@") || !email.contains(".")) {
            emailError = "Email braucht @ und ."
        } else if (containsForbiddenSqlChars(email)) {
            emailError = "Ungültige Zeichen in Email."
        }
    }

    if (address.isNotBlank()) {
        if (address.length > 50) {
            addressError = "Adresse maximal 50 Zeichen."
        } else if (containsForbiddenSqlChars(address)) {
            addressError = "Ungültige Zeichen in Adresse."
        }
    }

    if (note.isNotBlank()) {
        if (note.length > 500) {
            noteError = "Notiz maximal 500 Zeichen."
        } else if (containsForbiddenSqlChars(note)) {
            noteError = "Ungültige Zeichen in Notiz."
        }
    }

    if (pictureUrl.isNotBlank()) {
        if (pictureUrl.length > 100) {
            pictureUrlError = "Bild-URL maximal 100 Zeichen."
        } else if (containsForbiddenSqlChars(pictureUrl)) {
            pictureUrlError = "Ungültige Zeichen in Bild-URL."
        }
    }

    return ContactValidationResult(
        nameError = nameError,
        phoneError = phoneError,
        emailError = emailError,
        addressError = addressError,
        noteError = noteError,
        pictureUrlError = pictureUrlError
    )
}