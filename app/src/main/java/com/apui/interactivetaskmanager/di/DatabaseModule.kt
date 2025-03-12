package com.apui.interactivetaskmanager.di

import com.apui.interactivetaskmanager.data.local.TaskDatabase
import com.apui.interactivetaskmanager.data.repository.TaskRepositoryImpl
import com.apui.interactivetaskmanager.domain.repository.TaskRepository
import com.apui.interactivetaskmanager.ui.screens.home.TaskListViewModel
import com.apui.interactivetaskmanager.ui.screens.taskcreation.TaskInsertViewModel
import com.apui.interactivetaskmanager.ui.screens.taskdetails.TaskDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single {
        TaskDatabase.getDatabase(get()).taskDao()
    }
    single<TaskRepository> {
        TaskRepositoryImpl(get())
    }
    single<TaskListViewModel> {
        TaskListViewModel(get(), get())
    }
    viewModel<TaskInsertViewModel> {
        TaskInsertViewModel(get())
    }
    viewModel {
        TaskDetailsViewModel(get())
    }
}