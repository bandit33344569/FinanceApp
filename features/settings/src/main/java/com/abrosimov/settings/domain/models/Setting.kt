package com.abrosimov.settings.domain.models

/**
 * Класс, представляющий элемент настроек в UI.
 *
 * Используется для построения списка настроек, где каждый элемент содержит:
 * - Заголовок (`title`)
 * - Описание (`description`, необязательное)
 * - Обработчик нажатия (`onClick`)
 *
 * @property title Заголовок настройки (обязательный).
 * @property description Описание настройки (необязательное, может быть null).
 * @property onClick Действие, выполняемое при нажатии на элемент настройки.
 */
data class Setting(
    val title: String,
    val description: String? = null,
    val onClick: () -> Unit
) {
}