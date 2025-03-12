package com.apui.interactivetaskmanager.domain.repository

import com.apui.interactivetaskmanager.data.model.TaskEntity
import com.apui.interactivetaskmanager.data.model.TaskStatus
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasksByDueDate(): Flow<List<TaskEntity>>
    fun getAllTasksByPriority(): Flow<List<TaskEntity>>
    fun getAllTasksByTitle(): Flow<List<TaskEntity>>
    suspend fun insertTask(task: TaskEntity)
    suspend fun deleteTask(task: TaskEntity)
    suspend fun updateTaskStatus(taskId: Int, taskStatus: TaskStatus)
    fun filterTasksByStatus(taskStatus: List<String>): Flow<List<TaskEntity>>
}