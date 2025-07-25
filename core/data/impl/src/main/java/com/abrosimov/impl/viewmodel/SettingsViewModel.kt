package com.abrosimov.impl.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abrosimov.impl.repository.AppSettingsRepository
import com.abrosimov.utils.theme.AppColor
import com.abrosimov.utils.theme.ThemeMode
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val settingsRepository: AppSettingsRepository
) : ViewModel() {

    val themeMode = settingsRepository.themeModeFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ThemeMode.LIGHT
    )

    val colorPreset = settingsRepository.colorPresetFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AppColor.GREEN
    )

    val isDarkTheme = themeMode.map { it == ThemeMode.DARK }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false
    )

    fun toggleTheme() {
        viewModelScope.launch {
            val current = themeMode.value
            val next = when (current) {
                ThemeMode.DARK -> ThemeMode.LIGHT
                ThemeMode.LIGHT -> ThemeMode.DARK
            }
            settingsRepository.saveThemeMode(next)
        }
    }

    fun saveColorPreset(preset: AppColor) {
        viewModelScope.launch {
            settingsRepository.saveColorPreset(preset)
        }
    }
}