package com.apui.interactivetaskmanager.data.repository

import com.apui.interactivetaskmanager.data.local.SortPreferences
import com.apui.interactivetaskmanager.domain.repository.SortPreferencesRepository

class SortPreferencesRepositoryImpl(private val sortPreferences: SortPreferences) :
    SortPreferencesRepository {
    override fun getSortOption() = sortPreferences.selectedSortOption

    override suspend fun saveSortOption(sortOption: String) {
        sortPreferences.saveSortOption(sortOption)
    }
}