package com.example.ft_hangouts.ui.helpfunctions

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

fun makePhoneCall(context: Context, phoneNumber: String) {
    if (phoneNumber.isNotBlank()) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        context.startActivity(intent)
    }
}

@Composable
fun PhoneCallButton(
    phoneNumber: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    IconButton(
        onClick = { makePhoneCall(context, phoneNumber) },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Phone,
            contentDescription = "Anrufen",
        )
    }
}