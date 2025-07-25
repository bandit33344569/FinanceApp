package com.abrosimov.impl.repository

import com.abrosimov.impl.DataStore.AppSettingsDataStore
import com.abrosimov.utils.theme.AppColor
import com.abrosimov.utils.theme.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppSettingsRepository @Inject constructor(
    private val dataStore: AppSettingsDataStore
) {
    val themeModeFlow: Flow<ThemeMode> = dataStore.themeFlow.map { themeString ->
        when (themeString) {
            AppSettingsDataStore.THEME_LIGHT -> ThemeMode.LIGHT
            AppSettingsDataStore.THEME_DARK -> ThemeMode.DARK
            else -> ThemeMode.LIGHT
        }
    }

    val colorPresetFlow: Flow<AppColor> = dataStore.colorPresetFlow

    suspend fun saveThemeMode(themeMode: ThemeMode) {
        val themeString = when (themeMode) {
            ThemeMode.LIGHT -> AppSettingsDataStore.THEME_LIGHT
            ThemeMode.DARK -> AppSettingsDataStore.THEME_DARK
        }
        dataStore.saveTheme(themeString)
    }

    suspend fun saveColorPreset(preset: AppColor) {
        dataStore.saveColorPreset(preset)
    }
}