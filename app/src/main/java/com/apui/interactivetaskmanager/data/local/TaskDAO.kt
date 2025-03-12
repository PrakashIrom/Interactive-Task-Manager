package com.apui.interactivetaskmanager.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.apui.interactivetaskmanager.data.model.TaskEntity
import com.apui.interactivetaskmanager.data.model.TaskStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDAO {
    @Query("SELECT * FROM tasks ORDER BY dueDate asc")
    fun getAllTasksByDate(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks ORDER BY priority desc")
    fun getAllTasksByPriority(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks ORDER BY title asc")
    fun getAllTasksByTitle(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks where taskStatus IN (:taskStatus)")
    fun filterTasksByStatus(taskStatus: List<String>): Flow<List<TaskEntity>>

    @Query("UPDATE tasks SET taskStatus = :taskStatus WHERE id = :taskId")
    fun updateTaskStatus(taskId: Int, taskStatus: TaskStatus)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)
}