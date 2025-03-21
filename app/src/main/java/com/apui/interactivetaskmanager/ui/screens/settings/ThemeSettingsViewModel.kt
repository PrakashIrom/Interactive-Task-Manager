package com.apui.interactivetaskmanager.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apui.interactivetaskmanager.domain.repository.ThemeSettingsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ThemeSettingsViewModel(private val themeSettingsRepo: ThemeSettingsRepo) : ViewModel() {
    private var _isDarkMode = MutableStateFlow(false)
    val isDarkMode = _isDarkMode

    init {
        viewModelScope.launch {
            themeSettingsRepo.getThemeSettings().collect { value ->
                _isDarkMode.value = value
            }
        }
    }

    fun toggleTheme() {
        viewModelScope.launch {
            themeSettingsRepo.saveThemeSettings(!_isDarkMode.value)
        }
    }
}