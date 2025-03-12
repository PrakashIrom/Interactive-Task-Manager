package com.apui.interactivetaskmanager.ui.screens.taskdetails

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apui.interactivetaskmanager.data.model.TaskEntity
import com.apui.interactivetaskmanager.data.model.TaskStatus
import com.apui.interactivetaskmanager.domain.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskDetailsViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    private val _sortList = mutableStateListOf<TaskEntity>()
    val sortList = _sortList

    fun updateTaskStatus(taskId: Int, taskStatus: TaskStatus) {
        viewModelScope.launch {
            taskRepository.updateTaskStatus(taskId, taskStatus)
        }
    }

}