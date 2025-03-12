package com.apui.interactivetaskmanager.data.repository

import com.apui.interactivetaskmanager.data.local.TaskDAO
import com.apui.interactivetaskmanager.data.model.TaskEntity
import com.apui.interactivetaskmanager.data.model.TaskStatus
import com.apui.interactivetaskmanager.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(private val taskDao: TaskDAO) : TaskRepository {

    override fun getAllTasksByDueDate(): Flow<List<TaskEntity>> = taskDao.getAllTasksByDate()

    override fun getAllTasksByPriority(): Flow<List<TaskEntity>> = taskDao.getAllTasksByPriority()

    override fun getAllTasksByTitle(): Flow<List<TaskEntity>> = taskDao.getAllTasksByTitle()

    override suspend fun insertTask(task: TaskEntity) {
        taskDao.insertTask(task)
    }

    override suspend fun deleteTask(task: TaskEntity) {
        taskDao.deleteTask(task)
    }

    override suspend fun updateTaskStatus(taskId: Int, taskStatus: TaskStatus) {
        taskDao.updateTaskStatus(taskId, taskStatus)
    }

    override fun filterTasksByStatus(taskStatus: List<String>): Flow<List<TaskEntity>> =
        taskDao.filterTasksByStatus(taskStatus)

}