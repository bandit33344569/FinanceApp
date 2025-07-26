package com.abrosimov.impl.DataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.abrosimov.utils.theme.AppColor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


private const val SETTINGS_DATASTORE = "app_settings"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SETTINGS_DATASTORE)

class AppSettingsDataStore @Inject constructor(
    private val context: Context
) {
    companion object {
        private val THEME_KEY = stringPreferencesKey("theme")
        private val COLOR_PRESET_KEY = stringPreferencesKey("color_preset")

        const val THEME_SYSTEM = "system"
        const val THEME_LIGHT = "light"
        const val THEME_DARK = "dark"
        const val DEFAULT_COLOR_PRESET = "GREEN"
    }

    val themeFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[THEME_KEY] ?: THEME_SYSTEM
        }

    val colorPresetFlow: Flow<AppColor> = context.dataStore.data
        .map { preferences ->
            val presetName = preferences[COLOR_PRESET_KEY] ?: DEFAULT_COLOR_PRESET
            AppColor.valueOf(presetName)
        }

    suspend fun saveTheme(theme: String) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme
        }
    }

    suspend fun saveColorPreset(preset: AppColor) {
        context.dataStore.edit { preferences ->
            preferences[COLOR_PRESET_KEY] = preset.name
        }
    }
}