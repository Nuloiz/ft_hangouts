package com.example.ft_hangouts.ui.helpfunctions

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

object HeaderColorManager {
    private const val PREFS_NAME = "app_settings"
    private const val KEY_HEADER_COLOR = "header_color"
    val DEFAULT_COLOR = Color(0xFF6200EE) //purple

    val AVAILABLE_COLORS = listOf(
        Color(0xFF6200EE), // purple (Standard)
        Color(0xFF2196F3), // blue
        Color(0xFF03A9F4), // light blue
        Color(0xFF00BCD4), // cyan
        Color(0xFF4CAF50), // green
        Color(0xFFFFEB3B), // yellow
        Color(0xFFFF9800), // orange
        Color(0xFFE91E63), // pink
        Color(0xFF8B0000), // crimson
        Color(0xFF795548), // brown
        Color(0xFF9E9E9E), // grey
        Color(0xFF607D8B), // blue-grey
        Color(0xFF000000), // black
        Color(0xFFFFFFFF)  // white
    )

    fun getSavedColor(context: Context): Color {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val colorInt = prefs.getInt(KEY_HEADER_COLOR, DEFAULT_COLOR.toArgb())
        return Color(colorInt)
    }

    fun saveColor(context: Context, color: Color) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putInt(KEY_HEADER_COLOR, color.toArgb()).apply()
    }
}