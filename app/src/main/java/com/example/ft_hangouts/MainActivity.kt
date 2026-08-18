package com.example.ft_hangouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.ft_hangouts.ui.AppScreens.*
import com.example.ft_hangouts.ui.AppScreens
import com.example.ft_hangouts.ui.AppScreenRouter
import com.example.ft_hangouts.db.ContactDao
import com.example.ft_hangouts.db.MessageDao
import com.example.ft_hangouts.ui.theme.Ft_hangoutsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val contactDao = ContactDao(this)
        val messageDao = MessageDao(this)
        setContent {
            Ft_hangoutsTheme {
                var currentScreen by remember { mutableStateOf<AppScreens>(Start) }
                AppScreenRouter(
                    currentScreen = currentScreen,
                    onNavigate = { newScreen: AppScreens -> currentScreen = newScreen },
                    contactDao = contactDao,
                    messageDao = messageDao
                )
            }
        }
        if (checkSelfPermission(android.Manifest.permission.RECEIVE_SMS) != android.content.pm.PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(android.Manifest.permission.SEND_SMS) != android.content.pm.PackageManager.PERMISSION_GRANTED) { //Force Permission Request
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.RECEIVE_SMS,
                    android.Manifest.permission.SEND_SMS,
                    android.Manifest.permission.READ_SMS
                ), 101
            )
        }
    }
}