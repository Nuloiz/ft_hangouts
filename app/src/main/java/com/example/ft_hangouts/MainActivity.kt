package com.example.ft_hangouts

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
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
        val buttonTestdb: Button = findViewById(R.id.buttonTestdb)
        buttonTestdb.setOnClickListener {
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

            Toast.makeText(
                this,
                "DB OK: ${allContacts.size} Kontakte",
                Toast.LENGTH_SHORT
            ).show()
        }
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