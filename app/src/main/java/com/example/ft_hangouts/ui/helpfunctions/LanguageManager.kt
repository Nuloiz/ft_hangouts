package com.example.ft_hangouts.ui.helpfunctions

import android.content.Context

object LanguageManager {
    private const val PREFS_NAME = "app_settings"
    private const val KEY_LANGUAGE = "app_language"

    const val LANG_DE = "de"
    const val LANG_EN = "en"

    fun getSavedLanguage(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_LANGUAGE, LANG_DE) ?: LANG_DE
    }

    fun saveLanguage(context: Context, langCode: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_LANGUAGE, langCode).apply()
    }
}