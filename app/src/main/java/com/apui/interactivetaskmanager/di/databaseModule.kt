package com.apui.interactivetaskmanager.di

import com.apui.interactivetaskmanager.data.local.TaskDatabase
import com.apui.interactivetaskmanager.data.repository.TaskRepositoryImpl
import com.apui.interactivetaskmanager.domain.repository.TaskRepository
import com.apui.interactivetaskmanager.ui.screens.home.TaskListViewModel
import com.apui.interactivetaskmanager.ui.screens.taskcreation.TaskInsertViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single {
        TaskDatabase.getDatabase(get()).taskDao()
    }
    single<TaskRepository> {
        TaskRepositoryImpl(get())
    }
    viewModel<TaskListViewModel> {
        TaskListViewModel(get())
    }
    viewModel<TaskInsertViewModel> {
        TaskInsertViewModel(get())
    }
}