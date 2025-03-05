package com.apui.interactivetaskmanager.di

import com.apui.interactivetaskmanager.ui.screens.TopBarViewModel
import org.koin.dsl.module

val topBarViewModelModule = module{
    single<TopBarViewModel>{
        TopBarViewModel()
    }
}