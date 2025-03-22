package com.apui.interactivetaskmanager.ui.screens.taskdetails

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProgressBarViewModel : ViewModel() {
    private val _taskProgress = MutableStateFlow(0f)
    val taskProgress: StateFlow<Float> = _taskProgress

    fun updateProgress() {
        _taskProgress.value = (_taskProgress.value + 0.1f).coerceAtMost(1f)
    }
}