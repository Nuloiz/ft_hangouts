package com.example.ft_hangouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.ft_hangouts.ui.AppScreens.*
import com.example.ft_hangouts.ui.AppScreenRouter
import com.example.ft_hangouts.db.ContactDao
import com.example.ft_hangouts.ui.theme.Ft_hangoutsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val contactDao = ContactDao(this)
        setContent {
            Ft_hangoutsTheme {
                val currentScreen by remember { mutableStateOf(START) }
                AppScreenRouter(currentScreen = currentScreen, contactDao = contactDao)
            }
        }
    }
}