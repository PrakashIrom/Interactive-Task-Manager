package com.apui.interactivetaskmanager.domain.repository

import kotlinx.coroutines.flow.Flow

interface ThemeSettingsRepo {
    fun getThemeSettings(): Flow<Boolean>
    suspend fun saveThemeSettings(isDarkMode: Boolean)
}