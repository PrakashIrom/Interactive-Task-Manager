package com.apui.interactivetaskmanager.domain.repository

import com.apui.interactivetaskmanager.data.model.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasksByDueDate(): Flow<List<TaskEntity>>
    fun getAllTasksByPriority(): Flow<List<TaskEntity>>
    suspend fun insertTask(task: TaskEntity)
}