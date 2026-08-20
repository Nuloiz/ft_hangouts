package com.example.ft_hangouts

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.ft_hangouts.ui.AppScreens.*
import com.example.ft_hangouts.ui.AppScreens
import com.example.ft_hangouts.ui.AppScreenRouter
import com.example.ft_hangouts.db.ContactDao
import com.example.ft_hangouts.db.MessageDao
import com.example.ft_hangouts.ui.theme.Ft_hangoutsTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val contactDao = ContactDao(this)
        val messageDao = MessageDao(this)
        setContent {
            BackgroundLifecycleTracker()
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

@Composable
fun BackgroundLifecycleTracker() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var backgroundStartTime by remember { mutableStateOf(0L) }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                backgroundStartTime = System.currentTimeMillis()
            } else if (event == Lifecycle.Event.ON_START) {
                if (backgroundStartTime > 0L) {
                    val formattedTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                        .format(Date(backgroundStartTime))
                    Toast.makeText(
                        context,
                        "App was set to background at $formattedTime",
                        Toast.LENGTH_LONG
                    ).show()
                    backgroundStartTime = 0L
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}