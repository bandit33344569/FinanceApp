package com.abrosimov.financeapp.ui.navigation.screensconfigs

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

/**
 * Класс, представляющий конфигурацию экрана.
 *
 * Содержит параметры, необходимые для построения интерфейса экрана:
 * - Заголовок
 * - Иконка навигации (слева в AppBar)
 * - Дополнительные действия (справа в AppBar)
 * - Видимость и логика Floating Action Button (FAB)
 *
 * @property title Заголовок экрана, отображаемый в AppBar.
 * @property navigationIcon Композируемая функция, отрисовывающая иконку навигации.
 * @property actions Композируемая функция, отрисовывающая дополнительные действия в AppBar.
 * @property fabVisibility Определяет, должен ли быть виден FAB на этом экране.
 * @property fabOnClick Логика, выполняемая при нажатии на FAB. Может быть null.
 */
open class ScreenConfig(
    val title: String,
    val navigationIcon: @Composable () -> Unit = {},
    val actions: @Composable RowScope.() -> Unit = {},
    val fabVisibility: Boolean = false,
    val fabOnClick: (() -> Unit)? = null
)
