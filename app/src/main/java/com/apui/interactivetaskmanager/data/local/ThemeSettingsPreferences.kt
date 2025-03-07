package com.apui.interactivetaskmanager.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/*Top-level declaration ensures a single instance of DataStore is created and reused.
It enables lazy initialization, thread safety, and easy dependency injection*/
private val Context.themeDataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_settings")

class ThemeSettingsPreferences(private val context: Context) {

    private val THEME_KEY = booleanPreferencesKey("dark_mode")

    val getThemeFlow: Flow<Boolean> = context.themeDataStore.data.map { preferences ->
        preferences[THEME_KEY] ?: false
    }

    suspend fun saveTheme(isDarkMode: Boolean) {
        context.themeDataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkMode
        }
    }

}