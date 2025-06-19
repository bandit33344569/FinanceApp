package com.abrosimov.financeapp.domain.models.mock

import com.abrosimov.financeapp.domain.models.Setting

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