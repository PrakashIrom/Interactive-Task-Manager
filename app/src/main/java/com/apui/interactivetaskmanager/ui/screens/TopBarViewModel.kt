package com.apui.interactivetaskmanager.ui.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.apui.interactivetaskmanager.utils.Screens

class TopBarViewModel : ViewModel() {
    private val _selectedScreen = mutableStateOf(Screens.HOME)
    val selectedScreen: State<Screens> = _selectedScreen

    fun currentTop(currentScreen: Screens) {
        this._selectedScreen.value = currentScreen
    }
}