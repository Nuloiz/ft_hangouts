package com.example.ft_hangouts.ui

import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.ft_hangouts.db.Contact
import com.example.ft_hangouts.db.ContactDao

@Composable
fun Testbuttondb(contactDao: ContactDao) {
    val context = LocalContext.current

    Button(onClick = {
        val newId = contactDao.insertContact(
            Contact(
                name = "Test User",
                phoneNumber = "017856789",
                email = "test@example.com",
                address = "Teststraße 1",
                note = "Testkontakt",
                pictureurl = null
            )
        )
        val allContacts = contactDao.getAllContacts()

        Toast.makeText(
            context,
            "DB OK: ${allContacts.size} Kontakte",
            Toast.LENGTH_SHORT
        ).show()
    }) {
        Text("Test DB")
    }
}
