package com.apui.interactivetaskmanager.domain.repository

import kotlinx.coroutines.flow.Flow

interface SortPreferencesRepository {
    fun getSortOption(): Flow<String>
    suspend fun saveSortOption(sortOption: String)
}