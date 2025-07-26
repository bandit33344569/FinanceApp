package com.abrosimov.impl.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abrosimov.impl.repository.AppSettingsRepository
import javax.inject.Inject


class SettingsViewModelFactory @Inject constructor(
    private val settingsRepository: AppSettingsRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        SettingsViewModel(
            settingsRepository = settingsRepository
        ) as T
}