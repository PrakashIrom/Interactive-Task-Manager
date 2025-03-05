package com.apui.interactivetaskmanager.data.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.apui.interactivetaskmanager.data.model.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskDAO {
    @Query("SELECT * FROM tasks ORDER BY dueDate desc")
    fun getAllTasksByDate(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks ORDER BY priority desc")
    fun getAllTasksByPriority(): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)
}