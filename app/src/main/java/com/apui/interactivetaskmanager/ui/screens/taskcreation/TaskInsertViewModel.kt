package com.apui.interactivetaskmanager.ui.screens.taskcreation

import androidx.lifecycle.ViewModel
import com.apui.interactivetaskmanager.data.model.TaskEntity
import com.apui.interactivetaskmanager.domain.repository.TaskRepository

class TaskInsertViewModel(private val taskRepository: TaskRepository): ViewModel() {

    suspend fun insertTask(task: TaskEntity) {
        taskRepository.insertTask(task)
    }

}