package com.example.ft_hangouts.ui.helpfunctions

object Translations {
    private val translations = mapOf(
        // --- General / Navigation ---
        "settings" to mapOf("de" to "Einstellungen", "en" to "Settings"),
        "main_screen" to mapOf("de" to "Chats", "en" to "Main Screen"),
        "contacts" to mapOf("de" to "Kontakte", "en" to "Contacts"),
        "refresh" to mapOf("de" to "Aktualisieren", "en" to "Refresh"),
        "cancel" to mapOf("de" to "Abbrechen", "en" to "Cancel"),
        "return" to mapOf("de" to "Zurück", "en" to "Return"),

        // --- Settings Screen ---
        "header_color" to mapOf("de" to "Header-Farbe", "en" to "Header Color"),
        "language" to mapOf("de" to "Sprache", "en" to "Language"),
        "to_main_screen" to mapOf("de" to "Zum Hauptbildschirm", "en" to "To Main Screen"),
        "back_to_the_roots" to mapOf("de" to "Zurück zu den Wurzeln", "en" to "Back to the roots"),

        // --- Main / Chat Screen ---
        "chat_with" to mapOf("de" to "Chat mit ", "en" to "Chat with "),
        "new_chat" to mapOf("de" to "Neuer Chat", "en" to "New Chat"),
        "empty_chat" to mapOf("de" to "Leerer Chat", "en" to "Chat empty"),
        "start_chat" to mapOf("de" to "Neuen Chat starten", "en" to "Start new chat"),
        "select_contact" to mapOf("de" to "Kontakt auswählen", "en" to "Select contact"),
        "new_contact" to mapOf("de" to "Neuer Kontakt", "en" to "New contact"),
        "no_contact" to mapOf("de" to "Kein Kontakt verfuegbar", "en" to "No contact available"),
        "type_message" to mapOf("de" to "Nachricht schreiben...", "en" to "Type message..."),
        "send" to mapOf("de" to "Senden", "en" to "Send"),

        // --- Contact / Contact List Screen ---
        "del_success" to mapOf("de" to "Löschen erfolgreich", "en" to "Deletion successfull"),
        "del_failed" to mapOf("de" to "Löschen gescheitert", "en" to "Deletion failed"),
        "phone" to mapOf("de" to "Telefon", "en" to "Phone"),
        "address" to mapOf("de" to "Addresse", "en" to "Address"),
        "note" to mapOf("de" to "Notizen", "en" to "Note"),
        "address" to mapOf("de" to "Bild", "en" to "Picture"),
        "open_chat" to mapOf("de" to "Chat öffnen", "en" to "Open chat"),
        "delete_contact" to mapOf("de" to "Löschen", "en" to "Delete"),
        "edit_contact" to mapOf("de" to "Bearbeiten", "en" to "Edit"),
        "create_contact" to mapOf("de" to "Neuen Kontakt erstellen", "en" to "Create new contact"),

    )
    fun tr(key: String, lang: String): String {
        return translations[key]?.get(lang) ?: key
    }
}