package com.apui.interactivetaskmanager.ui.screens.taskcreation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apui.interactivetaskmanager.data.model.TaskEntity
import com.apui.interactivetaskmanager.domain.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskInsertViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    fun insertTask(task: TaskEntity) {
        viewModelScope.launch {
            taskRepository.insertTask(task)
        }
    }

}