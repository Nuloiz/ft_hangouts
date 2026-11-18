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
        "phone" to mapOf("de" to "Telefon: ", "en" to "Phone: "),
        "phone_number" to mapOf("de" to "Telefonnummer: ", "en" to "Phone number: "),
        "address" to mapOf("de" to "Addresse: ", "en" to "Address: "),
        "note" to mapOf("de" to "Notizen: ", "en" to "Notes: "),
        "open_chat" to mapOf("de" to "Chat öffnen", "en" to "Open chat"),
        "delete_contact" to mapOf("de" to "Löschen", "en" to "Delete"),
        "edit_contact" to mapOf("de" to "Bearbeiten", "en" to "Edit"),
        "create_contact" to mapOf("de" to "Neuen Kontakt erstellen", "en" to "Create new contact"),
        "edit_con" to mapOf("de" to "Kontakt bearbeiten", "en" to "Edit contact"),
        "create" to mapOf("de" to "Erstellen", "en" to "Create"),
        "save" to mapOf("de" to "Speichern", "en" to "Save"),

        // --- ContactForm ---
        "ChooPic" to mapOf("de" to "Bild auswählen", "en" to "Choose Picture"),
        "nameE1" to mapOf("de" to "Name darf nicht leer sein", "en" to "Name cannot be empty"),
        "nameE2" to mapOf("de" to "Name kann maximal 20 Zeichen lang sein", "en" to "Name cannot be longer than 20 characters"),
        "zeichenE" to mapOf("de" to "Enthält ungültige Zeichen", "en" to "Includes invalid characters"),
        "phoneE1" to mapOf("de" to "Telefonnummer darf nicht leer sein", "en" to "Phone number cannot be empty"),
        "phoneE2" to mapOf("de" to "Telefonnumer muss mit + beginnen", "en" to "Phone number has to start with +"),
        "phoneE3" to mapOf("de" to "Nur Ziffern sind erlaubt", "en" to "Only digits are allowed"),
        "phoneE4" to mapOf("de" to "Telefonnumer kann nur zwischen 5 - 20 Ziffern lang sein", "en" to "Phone number has to be betweeen 5 - 20 digits"),
        "mailE1" to mapOf("de" to "E-Mail kann maximal 500 Zeichen lang sein", "en" to "E-mail cannot be longer than 500 characters"),
        "mailE2" to mapOf("de" to "Keine Leerzeichen in der E-Mail erlaubt", "en" to "No Spaces allowed in E-mail"),
        "mailE3" to mapOf("de" to "E-Mail braucht ein @ und ein .", "en" to "E-mail needs an @ and a ."),
        "adressE1" to mapOf("de" to "Adresse kann maximal 100 Zeichen lang sein", "en" to "Adress cannot be longre than 100 chararcters"),
        "noteE1" to mapOf("de" to "Notizen können maximal 500 Zeichen lang sein", "en" to "Notes cannot be longer than 500 characters"),
        "pictureE1" to mapOf("de" to "Die Bild-URL kann maximal 100 Zeichen lang sein", "en" to "The picture-URL cannot be longer than 100 characters"),
    )
    fun tr(key: String, lang: String): String {
        return translations[key]?.get(lang) ?: key
    }
}