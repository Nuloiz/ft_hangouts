package com.example.ft_hangouts.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.db.ContactDao

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    contactDao: ContactDao
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "ft_hangouts",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(24.dp))
        Testbuttondb(contactDao = contactDao)
        Spacer(modifier = Modifier.height(16.dp))
        ShowContactListButton(contactDao = contactDao)
        Spacer(modifier = Modifier.height(16.dp))
    }
}
