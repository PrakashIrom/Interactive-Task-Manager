package com.apui.interactivetaskmanager.di

import com.apui.interactivetaskmanager.data.local.TaskDatabase
import com.apui.interactivetaskmanager.data.repository.TaskRepositoryImpl
import com.apui.interactivetaskmanager.domain.repository.TaskRepository
import org.koin.dsl.module

val databaseModule = module {
    single {
        TaskDatabase.getDatabase(get())
    }
    single<TaskRepository> {
        TaskRepositoryImpl(get())
    }
}