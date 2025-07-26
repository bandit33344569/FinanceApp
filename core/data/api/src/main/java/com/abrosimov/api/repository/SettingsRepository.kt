package com.abrosimov.api.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun saveTheme(theme: String)
    val themeFlow: Flow<String>
    suspend fun saveColorPreset(preset: String)
    val colorPresetFlow: Flow<String>
}