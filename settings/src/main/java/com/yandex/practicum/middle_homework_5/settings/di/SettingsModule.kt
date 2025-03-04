package com.yandex.practicum.middle_homework_5.settings.di

import com.yandex.practicum.middle_homework_5.settings.SettingsViewModel
import com.yandex.practicum.middle_homework_5.settings.datastore.SettingsRepository
import com.yandex.practicum.middle_homework_5.settings.datastore.SettingsRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {
    single<SettingsRepository> { SettingsRepositoryImpl(androidApplication()) }
    viewModel { SettingsViewModel(get()) }
}