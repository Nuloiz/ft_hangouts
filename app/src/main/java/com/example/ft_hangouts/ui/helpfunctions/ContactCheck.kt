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
    currentLanguage: String,
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
        nameError = Translations.tr("nameE1", currentLanguage)
    } else if (name.length > 20) {
        nameError = Translations.tr("nameE2", currentLanguage)
    } else if (containsForbiddenSqlChars(name)) {
        nameError = Translations.tr("zeichenE", currentLanguage)
    }

    if (phone.isBlank()) {
        phoneError = Translations.tr("phoneE1", currentLanguage)
    } else if (!phone.startsWith("+")) {
        phoneError = Translations.tr("phoneE2", currentLanguage)
    } else {
        val digits = phone.drop(1)
        if (!digits.all { it.isDigit() }) {
            phoneError = Translations.tr("phoneE3", currentLanguage)
        }
    }
    if (phone.length < 5 || phone.length > 20) {
        phoneError = Translations.tr("phoneE4", currentLanguage)
    }

    if (email.isNotBlank()) {
        if (email.length > 50) {
            emailError = Translations.tr("mailE1", currentLanguage)
        } else if (' ' in email || '\n' in email) {
            emailError = Translations.tr("mailE2", currentLanguage)
        } else if (!email.contains("@") || !email.contains(".")) {
            emailError = Translations.tr("mailE3", currentLanguage)
        } else if (containsForbiddenSqlChars(email)) {
            emailError = Translations.tr("zeichenE", currentLanguage)
        }
    }

    if (address.isNotBlank()) {
        if (address.length > 100) {
            addressError = Translations.tr("adressE1", currentLanguage)
        } else if (containsForbiddenSqlChars(address)) {
            addressError = Translations.tr("zeichenE", currentLanguage)
        }
    }

    if (note.isNotBlank()) {
        if (note.length > 500) {
            noteError = Translations.tr("noteE1", currentLanguage)
        } else if (containsForbiddenSqlChars(note)) {
            noteError = Translations.tr("zeichenE", currentLanguage)
        }
    }

    if (pictureUrl.isNotBlank()) {
        if (pictureUrl.length > 100) {
            pictureUrlError = Translations.tr("pictureE1", currentLanguage)
        } else if (containsForbiddenSqlChars(pictureUrl)) {
            pictureUrlError = Translations.tr("zeichenE", currentLanguage)
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