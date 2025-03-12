package com.apui.interactivetaskmanager.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.sortDataStore: DataStore<Preferences> by preferencesDataStore(name = "sort_preferences")

class SortPreferences(private val context: Context) {
    private val SORT_KEY = stringPreferencesKey("sort_option")

    val selectedSortOption: Flow<String> = context.sortDataStore.data.map { preferences ->
        preferences[SORT_KEY] ?: "Sort by Priority"
    }

    suspend fun saveSortOption(sortOption: String) {
        context.sortDataStore.edit { preferences ->
            preferences[SORT_KEY] = sortOption
        }
    }
}