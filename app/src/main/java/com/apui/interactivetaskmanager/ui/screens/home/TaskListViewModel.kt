package com.apui.interactivetaskmanager.ui.screens.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apui.interactivetaskmanager.data.model.TaskEntity
import com.apui.interactivetaskmanager.domain.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskListViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    private val _tasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val tasks: StateFlow<List<TaskEntity>> = _tasks

    init {
        showTaskByPriority()
    }

    private fun showTaskByPriority() {
        viewModelScope.launch {
            taskRepository.getAllTasksByPriority().collect { taskList ->
                _tasks.value = taskList
            }
        }

    }

    fun showTaskByDueDate() {
        viewModelScope.launch {
            taskRepository.getAllTasksByDueDate().collect { taskList ->
                _tasks.value = taskList
            }
        }
    }

    /*suspend fun insertTask(task: TaskEntity) {
        taskRepository.insertTask(task)
    }*/
}