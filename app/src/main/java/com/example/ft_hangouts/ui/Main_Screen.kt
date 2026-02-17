package com.example.ft_hangouts.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
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
        modifier = modifier.padding(16.dp)
    ) {
        Text(text = "ft_hangouts")
        Testbuttondb(contactDao = contactDao)
    }
}
