package com.example.ft_hangouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ft_hangouts.ui.theme.Ft_hangoutsTheme
import com.example.ft_hangouts.db.Contact
import com.example.ft_hangouts.db.ContactDao

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ft_hangoutsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        val contactDao = ContactDao(this)

// DB TEST
        /*
        val newId = contactDao.insertContact(
            Contact(
                name = "Test User",
                phoneNumber = "+49123456789",
                email = "test@example.com",
                address = "Teststraße 1",
                note = "Erster Testkontakt",
                pictureurl = null
            )
        )
        val allContacts = contactDao.getAllContacts()
        val insertedContact = contactDao.getContactById(newId)
        android.widget.Toast.makeText(
            this,
            "DB OK: ${allContacts.size} Kontakte",
            android.widget.Toast.LENGTH_SHORT
        ).show()
        */
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Ft_hangoutsTheme {
        Greeting("Android")
    }
}