package com.example.ft_hangouts.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.db.Contact
import com.example.ft_hangouts.db.ContactDao
import com.example.ft_hangouts.db.Message
import com.example.ft_hangouts.db.MessageDao
import com.example.ft_hangouts.ui.helpfunctions.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//Buttons: -> ChatScreen, -> ContactListScreen, -> Settingsscreen, -> NewChat (-> ExistingUser, -> NewUser), -> AktualisierenMain

data class MainChatItem(
    val contact : Contact,
    val latmessage : Message?
)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    contactDao: ContactDao,
    messageDao: MessageDao,
    headerColor: Color,
    onNavigateToSettings: () -> Unit,
    onNavigateToContactList: () -> Unit,
    onNavigateToChat: (Contact) -> Unit
) {
    fun loadChatItems(): List<MainChatItem> {
        return contactDao.getAllContacts().map { contact ->
            MainChatItem(
                contact = contact,
                latmessage = messageDao.getlatestMessageofContact(contact.id)
            )
        }.sortedByDescending { chatItem ->
            chatItem.latmessage?.timestamp ?: 0L
        }
    }
    var chatItems by remember { mutableStateOf(loadChatItems()) }
    fun reloadScreen() {chatItems = loadChatItems() }
    Column(
        modifier = modifier.fillMaxSize()) {
        AppHeader(title = "Main Screen", headerColor = headerColor)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ButtontoSettings(onNavigateToSettings = onNavigateToSettings, "Settings")
                Spacer(modifier = Modifier.height(16.dp))
                ButtontoContactList(onNavigateToContactList = onNavigateToContactList, "Contacts")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { reloadScreen() }) { Text("refresh") }
            }
            Spacer(modifier = Modifier.height(16.dp))
            ChatList(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                chatItems = chatItems,
                onContactClick = onNavigateToChat
            )
            NewChatButton(
                contactDao = contactDao,
                onNavigateToChat = onNavigateToChat,
                onContactCreated = { reloadScreen() })
        }
    }
}

@Composable
fun ChatList(
    modifier: Modifier = Modifier,
    chatItems: List<MainChatItem>,
    onContactClick: (Contact) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(chatItems) { item ->
            ChatListItem(
                item = item,
                onClick = { onContactClick(item.contact) }
            )
        }
    }
}

@Composable
fun ChatListItem(
    item: MainChatItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.contact.name,
                    style = MaterialTheme.typography.headlineSmall
                )
                item.latmessage?.let { msg ->
                    val dateStr = SimpleDateFormat("HH:mm - dd.MM.", Locale.getDefault())
                        .format(Date(msg.timestamp))
                    Text(
                        text = dateStr,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.latmessage?.body ?: "Empty Chat",
                style = MaterialTheme.typography.bodyMedium,
                color = if (item.latmessage != null) Color.Unspecified else Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun NewChatButton(
    modifier: Modifier = Modifier,
    contactDao: ContactDao,
    onNavigateToChat: (Contact) -> Unit,
    onContactCreated: () -> Unit
) {
    var showSelectionDialog by remember { mutableStateOf(false) }
    var showContactPickerList by remember { mutableStateOf(false) }
    var showCreateContactDialog by remember { mutableStateOf(false) }
    Button(
        onClick = { showSelectionDialog = true },
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(vertical = 8.dp)
    ) { Text("New Chat") }
    if (showSelectionDialog) {
        AlertDialog(
            onDismissRequest = { showSelectionDialog = false },
            title = { Text("Start New Chat") },
            text = { Text("New or Existing Contact") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSelectionDialog = false
                        showContactPickerList = true
                    }
                ) { Text("Choose Contact") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showSelectionDialog = false
                        showCreateContactDialog = true
                    }
                ) { Text("New Contact") }
            }
        )
    }
    if (showContactPickerList) {
        val contacts = remember { contactDao.getAllContacts() }

        AlertDialog(
            onDismissRequest = { showContactPickerList = false },
            title = { Text("Choose Contact") },
            text = {
                if (contacts.isEmpty()) {
                    Text("No Contacts")
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 300.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(contacts) { contact ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        showContactPickerList = false
                                        onNavigateToChat(contact)
                                    }
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = contact.name,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = contact.phoneNumber,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showContactPickerList = false }) {
                    Text("Return")
                }
            }
        )
    }
    if (showCreateContactDialog) {
        ContactFormDialog(
            isCreateMode = true,
            onDismiss = { showCreateContactDialog = false },
            onSave = { newContact ->
                val id = contactDao.insertContact(newContact)
                showCreateContactDialog = false
                onContactCreated()
                val savedContact = newContact.copy(id = id)
                onNavigateToChat(savedContact)
            }
        )
    }
}
