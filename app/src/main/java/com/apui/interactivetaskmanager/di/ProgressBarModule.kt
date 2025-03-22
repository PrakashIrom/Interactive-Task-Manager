package com.apui.interactivetaskmanager.di

import com.apui.interactivetaskmanager.ui.screens.taskdetails.ProgressBarViewModel
import org.koin.dsl.module

val progressBarModule = module {
    single { ProgressBarViewModel() }
}