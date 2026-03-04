package com.example.ft_hangouts.ui.helpfunctions

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.R
import com.example.ft_hangouts.db.Contact

@Composable
fun GalleryImagePickerField(
    pictureUrl: String,
    onPictureUrlChanged: (String?) -> Unit
) {
    val context = LocalContext.current
    val resolver = context.contentResolver
    var selectedUri by remember(pictureUrl) {
        mutableStateOf(
            pictureUrl.takeIf { it.isNotBlank() }?.let { Uri.parse(it) }
        )
    }
    var bitmap by remember(selectedUri) { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedUri = uri
        onPictureUrlChanged(uri?.toString())  // ← direkt an Dialog zurückgeben
    }
    LaunchedEffect(selectedUri) {
        bitmap = selectedUri?.let { loadBitmapFromUri(resolver, it) }
    }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(onClick = { launcher.launch("image/*") }) {
            Text("Choose picture")
        }
        bitmap?.let { bmp ->
            Image(
                bitmap = bmp.asImageBitmap(),
                contentDescription = "Selected profile image",
                modifier = Modifier.size(72.dp)
            )
        }
    }
}

private fun loadBitmapFromUri(
    resolver: android.content.ContentResolver,
    uri: Uri
): Bitmap? {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(resolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(resolver, uri)
        }
    } catch (e: Exception) {
        null
    }
}

@Composable
fun ContactPicture(contact: Contact?) {
    val context = LocalContext.current
    val resolver = context.contentResolver
    val pictureUrl = contact?.pictureurl
    var bitmap by remember(pictureUrl) { mutableStateOf<Bitmap?>(null) }
    LaunchedEffect(pictureUrl) {
        bitmap = null
        if (!pictureUrl.isNullOrBlank()) {
            try {
                val uri = Uri.parse(pictureUrl)
                bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    val source = ImageDecoder.createSource(resolver, uri)
                    ImageDecoder.decodeBitmap(source)
                } else {
                    @Suppress("DEPRECATION")
                    MediaStore.Images.Media.getBitmap(resolver, uri)
                }
            } catch (e: Exception) {
                bitmap = null
            }
        }
    }

    if (bitmap != null) {
        Image(
            bitmap = bitmap!!.asImageBitmap(),
            contentDescription = "Profile picture",
            modifier = Modifier.size(72.dp)
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.default_picture),
            contentDescription = "Default profile picture",
            modifier = Modifier.size(72.dp)
        )
    }
}