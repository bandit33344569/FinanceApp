package com.abrosimov.settings.domain.mock

import com.abrosimov.settings.domain.models.Setting

/**
 * Моковые данные для экрана настроек
 */
object SettingsMockData {
    fun getSettings(): List<Setting> = listOf(
        Setting(
            title = "Основной цвет",
            onClick = { }
        ),
        Setting(
            title = "Звуки",
            onClick = { }
        ),
        Setting(
            title = "Хаптики",
            onClick = { }
        ),
        Setting(
            title = "Код пароль",
            onClick = { }
        ),
        Setting(
            title = "Синхронизация",
            onClick = { }
        ),
        Setting(
            title = "Язык",
            onClick = { }
        ),
        Setting(
            title = "О программе",
            onClick = { }
        )
    )
}