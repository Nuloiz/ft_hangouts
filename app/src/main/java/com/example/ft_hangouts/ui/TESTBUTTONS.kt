package com.example.ft_hangouts.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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

@Composable
fun TestButtonContactList(contactDao: ContactDao) {
    var showList by remember { mutableStateOf(false) }
    Button(onClick = { showList = !showList }) {
        Text(if (showList) "Hide Contact List" else "Test Contact List")
    }
    if (showList) {
        TestContactList(contactDao = contactDao)
    }
}

@Composable
fun TestContactList(
    modifier: Modifier = Modifier,
    contactDao: ContactDao,
) {
    val contacts by remember { mutableStateOf(contactDao.getAllContacts()) }
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(contacts) { contact ->
            TestContactListItem(contact = contact)}
        }
    }

@Composable
fun TestContactListItem(
    contact: Contact,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
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
