package com.apui.interactivetaskmanager.data.repository

import com.apui.interactivetaskmanager.data.local.ThemeSettingsPreferences
import com.apui.interactivetaskmanager.domain.repository.ThemeSettingsRepo
import kotlinx.coroutines.flow.Flow

class ThemeSettingsRepoImpl(private val themeSettingsPreferences: ThemeSettingsPreferences) :
    ThemeSettingsRepo {
    override fun getThemeSettings(): Flow<Boolean> = themeSettingsPreferences.getThemeFlow

    override suspend fun saveThemeSettings(isDarkMode: Boolean) {
        themeSettingsPreferences.saveTheme(isDarkMode)
    }
}