package com.example.ft_hangouts.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.ui.helpfunctions.ButtontoMain
import com.example.ft_hangouts.ui.helpfunctions.HeaderColorManager

//Buttons: -> StartScreen, -> ChangeTheme, -> ChangeLanguage, -> ChangeTime

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    currentHeaderColor: Color,
    onHeaderColorChanged: (Color) -> Unit,
    onNavigateToMain: () -> Unit,
    onNavigateToStart: () -> Unit)
{
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(24.dp))
        HeaderColorPicker(
            currentColor = currentHeaderColor,
            onColorSelected = onHeaderColorChanged
        )
        Spacer(modifier = Modifier.height(32.dp))
        ButtontoMain(onNavigateToMain = onNavigateToMain, "Main Screen")
        Spacer(modifier = Modifier.height(24.dp))
        ButtonSettingstoStart(onNavigateToStart = onNavigateToStart)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun HeaderColorPicker(
    currentColor: Color,
    onColorSelected: (Color) -> Unit
) {
    val context = LocalContext.current
    val colorRows = HeaderColorManager.AVAILABLE_COLORS.chunked(6)
    Column {
        Text(
            text = "Header Color",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(12.dp))
        colorRows.forEach { rowColors ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                rowColors.forEach { color ->
                    val isSelected = color == currentColor
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = if (isSelected) 3.dp else 1.dp,
                                color = if (isSelected) Color.Black else Color.LightGray,
                                shape = CircleShape
                            )
                            .clickable {
                                HeaderColorManager.saveColor(context, color)
                                onColorSelected(color)
                            }
                    )
                }
            }
        }
    }
}
@Composable
fun ButtonSettingstoStart(onNavigateToStart: () -> Unit) {
    Button(onClick = onNavigateToStart) {
        Text("Back to the roots")
    }
}