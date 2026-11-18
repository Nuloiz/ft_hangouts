package com.example.ft_hangouts.ui

import android.content.res.Configuration
import android.telephony.SmsManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import com.example.ft_hangouts.db.Contact
import com.example.ft_hangouts.db.ContactDao
import com.example.ft_hangouts.db.Message
import com.example.ft_hangouts.db.MessageDao
import com.example.ft_hangouts.db.MessageDirection
import com.example.ft_hangouts.ui.helpfunctions.ContactPicture
import com.example.ft_hangouts.ui.helpfunctions.ButtontoMain
import com.example.ft_hangouts.ui.helpfunctions.ButtontoSettings
import com.example.ft_hangouts.ui.helpfunctions.PhoneCallButton
import com.example.ft_hangouts.ui.helpfunctions.Translations
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//Buttons: -> MainScreen, -> ContactScreen, -> Settingsscreen, -> AktualisierenChat, -> SendMessage
//Schreibfeld -> WriteMessage

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    contact: Contact,
    contactDao: ContactDao,
    messageDao: MessageDao,
    headerColor: Color,
    currentLanguage: String,
    onNavigateToMain: () -> Unit,
    onNavigateToContact: (Contact) -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    var messagesList by remember {
        mutableStateOf(messageDao.getMessagesofContact(contact.id))
    }
    fun refreshChat() {messagesList = messageDao.getMessagesofContact(contact.id)}
    LaunchedEffect(contact.id) {
        while (true) {
            delay(1000L)
            val latestMessages = messageDao.getMessagesofContact(contact.id)
            if (latestMessages.size != messagesList.size) {
                messagesList = latestMessages
            }
        }
    }
    val listState = rememberLazyListState()
    LaunchedEffect(messagesList.size) {
        if (messagesList.isNotEmpty()) {
            listState.animateScrollToItem(messagesList.size - 1)
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .imePadding()
    ) {
        ChatHeader(
            contact = contact,
            headerColor = headerColor,
            currentLanguage = currentLanguage,
            onNavigateToContact = onNavigateToContact
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val btnModifier = if (isLandscape) Modifier.weight(1f) else Modifier
                Button(
                    onClick = onNavigateToMain,
                    modifier = btnModifier,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = Translations.tr("to_main_screen", currentLanguage),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Button(
                    onClick = onNavigateToSettings,
                    modifier = btnModifier,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = Translations.tr("settings", currentLanguage),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(messagesList) {
                    message -> MessageBubble(message = message)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            MessageInputBar(
                contact = contact,
                messageDao = messageDao,
                currentLanguage = currentLanguage,
                onMessageSent = { refreshChat() }
            )
        }
    }
}

@Composable
fun ChatHeader(
    contact: Contact,
    headerColor: Color,
    currentLanguage: String,
    onNavigateToContact: (Contact) -> Unit
) {
    val textColor = if (headerColor.luminance() < 0.5f) Color.White else Color.Black
    val subTextColor = if (headerColor.luminance() < 0.5f) Color.LightGray else Color.DarkGray
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(headerColor)
            .statusBarsPadding()
            .clickable { onNavigateToContact(contact) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(56.dp)) {
            ContactPicture(contact = contact)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Text(
                text = Translations.tr("chat_with", currentLanguage) + contact.name,
                style = MaterialTheme.typography.headlineSmall,
                color = textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = contact.phoneNumber,
                style = MaterialTheme.typography.bodyMedium,
                color = subTextColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        PhoneCallButton(
            phoneNumber = contact.phoneNumber,
            tint = textColor,
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
fun MessageBubble(message: Message) {
    val isSent = message.direction == MessageDirection.SENT
    val alignment = if (isSent) Alignment.CenterEnd else Alignment.CenterStart
    val bubbleColor = if (isSent) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = alignment
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .background(bubbleColor, shape = RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Text(
                text = message.body,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = SimpleDateFormat("HH:mm - dd.MM", Locale.getDefault()).format(Date(message.timestamp)),
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
fun MessageInputBar(
    contact: Contact,
    messageDao: MessageDao,
    currentLanguage: String,
    onMessageSent: () -> Unit
) {
    var messageText by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = messageText,
            onValueChange = { messageText = it },
            placeholder = { Text(Translations.tr("type_message", currentLanguage)) },
            modifier = Modifier.weight(1f),
            maxLines = 3
        )
        Button(
            onClick = {
                if (messageText.isNotBlank()) {
                    try {
                        val smsManager: SmsManager = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                            context.getSystemService(SmsManager::class.java) ?: SmsManager.getDefault()
                        } else {
                            @Suppress("DEPRECATION")
                            SmsManager.getDefault()
                        }
                        smsManager.sendTextMessage(contact.phoneNumber, null, messageText, null, null)
                        val newMessage = Message(
                            contactId = contact.id,
                            body = messageText,
                            timestamp = System.currentTimeMillis(),
                            direction = MessageDirection.SENT
                        )
                        messageDao.insMessage(newMessage)
                        messageText = ""
                        onMessageSent()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        ) {
            Text(Translations.tr("send", currentLanguage))
        }
    }
}