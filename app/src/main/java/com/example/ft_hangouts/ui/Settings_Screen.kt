package com.example.ft_hangouts.ui

import android.content.res.Configuration
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.ui.helpfunctions.AppHeader
import com.example.ft_hangouts.ui.helpfunctions.ButtontoMain
import com.example.ft_hangouts.ui.helpfunctions.HeaderColorManager
import com.example.ft_hangouts.ui.helpfunctions.LanguageManager
import com.example.ft_hangouts.ui.helpfunctions.Translations
import com.example.ft_hangouts.ui.helpfunctions.CoordinateButton
//Buttons: -> StartScreen, -> ChangeTheme, -> ChangeLanguage, -> ChangeTime

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    headerColor: Color,
    currentHeaderColor: Color,
    currentLanguage: String,
    onHeaderColorChanged: (Color) -> Unit,
    onLanguageChanged: (String) -> Unit,
    onNavigateToMain: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.Start
    ) {
        AppHeader(
            title = Translations.tr("settings", currentLanguage),
            headerColor = headerColor,
        )
        if (isLandscape) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                HeaderColorPicker(
                    modifier = Modifier.weight(1f),
                    currentColor = currentHeaderColor,
                    currentLanguage = currentLanguage,
                    onColorSelected = onHeaderColorChanged
                )
                LanguageSelector(
                    modifier = Modifier.weight(1f),
                    currentLanguage = currentLanguage,
                    onLanguageSelected = onLanguageChanged
                )
            }
        } else {
            HeaderColorPicker(
                currentColor = currentHeaderColor,
                currentLanguage = currentLanguage,
                onColorSelected = onHeaderColorChanged
            )
            Spacer(modifier = Modifier.height(16.dp))
            LanguageSelector(
                currentLanguage = currentLanguage,
                onLanguageSelected = onLanguageChanged
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier.padding(16.dp)) {
            ButtontoMain(
                onNavigateToMain = onNavigateToMain,
                message = Translations.tr("to_main_screen", currentLanguage)
            )
        }
    }
}

@Composable
fun HeaderColorPicker(
    modifier: Modifier = Modifier,
    currentColor: Color,
    currentLanguage: String,
    onColorSelected: (Color) -> Unit
) {
    val context = LocalContext.current
    val colorRows = HeaderColorManager.AVAILABLE_COLORS.chunked(6)
    Column (modifier = modifier.padding(16.dp)){
        Text(
            text = Translations.tr(("header_color"), currentLanguage),
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
fun LanguageSelector(
    modifier: Modifier = Modifier,
    currentLanguage: String,
    onLanguageSelected: (String) -> Unit
) {
    val context = LocalContext.current
    Column (modifier = modifier.padding(16.dp)){
        Text(
            text = if (currentLanguage == LanguageManager.LANG_DE) "Sprache / Language" else "Language / Sprache",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    LanguageManager.saveLanguage(context, LanguageManager.LANG_DE)
                    onLanguageSelected(LanguageManager.LANG_DE)
                },
                colors = if (currentLanguage == LanguageManager.LANG_DE) {
                    ButtonDefaults.buttonColors()
                } else {
                    ButtonDefaults.outlinedButtonColors()
                }
            ) { Text("Deutsch 🇩🇪") }
            Button(
                onClick = {
                    LanguageManager.saveLanguage(context, LanguageManager.LANG_EN)
                    onLanguageSelected(LanguageManager.LANG_EN)
                },
                colors = if (currentLanguage == LanguageManager.LANG_EN) {
                    ButtonDefaults.buttonColors()
                } else {
                    ButtonDefaults.outlinedButtonColors()
                }
            ) { Text("English 🇬🇧") }
        }
    }
}