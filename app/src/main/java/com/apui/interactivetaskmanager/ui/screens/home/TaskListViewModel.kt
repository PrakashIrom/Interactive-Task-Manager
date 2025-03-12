package com.apui.interactivetaskmanager.ui.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apui.interactivetaskmanager.data.model.TaskEntity
import com.apui.interactivetaskmanager.data.model.TaskStatus
import com.apui.interactivetaskmanager.domain.repository.SortPreferencesRepository
import com.apui.interactivetaskmanager.domain.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val taskRepository: TaskRepository,
    private val sortPreferencesRepository: SortPreferencesRepository
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val tasks: StateFlow<List<TaskEntity>> = _tasks

    private val _isLoading = mutableStateOf(true)
    val isLoading = _isLoading

    private val _sortOption = mutableStateOf("")
    val sortOption = _sortOption

    private val _filterMapStatus = MutableStateFlow(
        TaskStatus.entries.associateWith { false }.toMutableMap()
    )
    val filterMapStatus: StateFlow<Map<TaskStatus, Boolean>> = _filterMapStatus

    init {
        showTasks()
    }

    private fun showTasks() {
        viewModelScope.launch {
            sortPreferencesRepository.getSortOption().collect { sortOption ->
                _sortOption.value = sortOption
                when (sortOption) {
                    "Sort by Priority" -> showTaskByPriority()
                    "Sort by Due Date" -> showTaskByDueDate()
                    else -> showTaskByAlphabeticalOrder()
                }
            }
        }
    }

    private fun showTaskByPriority() {
        viewModelScope.launch {
            taskRepository.getAllTasksByPriority().collect { taskList ->
                _tasks.value = taskList
                _isLoading.value = false
            }
        }
    }

    private fun showTaskByDueDate() {
        viewModelScope.launch {
            taskRepository.getAllTasksByDueDate().collect { taskList ->
                _tasks.value = taskList
                _isLoading.value = false
            }
        }
    }

    private fun showTaskByAlphabeticalOrder() {
        viewModelScope.launch {
            taskRepository.getAllTasksByTitle().collect { taskList ->
                _tasks.value = taskList
                _isLoading.value = false
            }
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }

    fun saveSortOption(sort: String) {
        viewModelScope.launch {
            sortPreferencesRepository.saveSortOption(sort)
        }
    }

    fun filterTasksByStatus(selectedStatuses: Set<TaskStatus>) {
        viewModelScope.launch(Dispatchers.IO) { // Ensure database calls are on IO
            val statusStrings = selectedStatuses.map { it.name }

            if (statusStrings.isNotEmpty()) {
                taskRepository.filterTasksByStatus(statusStrings)
                    .collect { taskList ->
                        _tasks.value = taskList
                        _isLoading.value = false
                    }
            } else {
                // If no filters are selected, fetch all tasks based on sorting
                showTasks()
            }
        }
    }

    fun updateTaskStatus(taskId: Int, taskStatus: TaskStatus) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.updateTaskStatus(taskId, taskStatus)
            _tasks.value = _tasks.value.map { task ->
                if (task.id == taskId) task.copy(taskStatus = taskStatus) else task
            } //Force UI recomposition with updated task list
        }
    }

    fun updateFilterStatus(status: TaskStatus, isChecked: Boolean) {
        _filterMapStatus.value = _filterMapStatus.value.toMutableMap().apply {
            this[status] = isChecked
        }
        filterTasksByStatus(_filterMapStatus.value.filterValues { it }.keys)
    }

}