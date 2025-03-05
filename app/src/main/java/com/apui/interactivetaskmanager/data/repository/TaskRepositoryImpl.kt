package com.apui.interactivetaskmanager.data.repository

import com.apui.interactivetaskmanager.data.local.TaskDAO
import com.apui.interactivetaskmanager.data.model.TaskEntity
import com.apui.interactivetaskmanager.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(private val taskDao: TaskDAO) : TaskRepository {

    override fun getAllTasksByDueDate(): Flow<List<TaskEntity>> = taskDao.getAllTasksByDate()

    override fun getAllTasksByPriority(): Flow<List<TaskEntity>> = taskDao.getAllTasksByPriority()

    override suspend fun insertTask(task: TaskEntity) {
        taskDao.insertTask(task)
    }

}