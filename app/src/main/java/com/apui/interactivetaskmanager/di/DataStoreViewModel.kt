package com.apui.interactivetaskmanager.di

import com.apui.interactivetaskmanager.data.local.ThemeSettingsPreferences
import com.apui.interactivetaskmanager.data.repository.ThemeSettingsRepoImpl
import com.apui.interactivetaskmanager.domain.repository.ThemeSettingsRepo
import org.koin.dsl.module

val dataStoreModule = module {
    single {
        ThemeSettingsPreferences(get())
    }
    single<ThemeSettingsRepo> {
        ThemeSettingsRepoImpl(get())
    }
}