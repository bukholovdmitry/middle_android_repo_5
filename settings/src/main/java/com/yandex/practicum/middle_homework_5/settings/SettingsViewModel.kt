package com.yandex.practicum.middle_homework_5.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.practicum.middle_homework_5.settings.datastore.SettingsRepository
import com.yandex.practicum.middle_homework_5.settings.ui.contract.SettingContainer
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    fun saveSetting(periodic: Long, delayed: Long) {
        viewModelScope.launch {
            settingsRepository.saveSetting(periodic = periodic, delayed = delayed)
        }
    }

    fun getCurrentSetting(): SettingContainer {
        return settingsRepository.settingData.value
    }
}