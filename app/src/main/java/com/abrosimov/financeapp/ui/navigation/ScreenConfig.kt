package com.abrosimov.financeapp.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation3.runtime.NavKey
import com.abrosimov.core.presentation.navigation.HistoryType
import com.abrosimov.financeapp.R

/**
 * Возвращает конфигурацию экрана на основе текущего ключа навигации.
 *
 * Формирует объект [ScreenConfig], содержащий информацию о:
 * - Заголовке экрана
 * - Иконке навигации (например, кнопка "Назад")
 * - Действиях в тулбаре (например, иконки с функционалом)
 * - Видимости и поведении FAB (FloatingActionButton)
 *
 * Поддерживает основные экраны приложения (`MainAppScreen`) и историю.
 * HystoryType - специальный тип, с помощью которого можно открыть соотвествующий экран(расходов или доходов)
 *
 * @param screen Текущий экран, представленный как [NavKey].
 * @param navigateToHistoryExpense Функция для перехода к истории расходов. Может быть null.
 * @param navigateToHistoryIncome Функция для перехода к истории доходов. Может быть null.
 * @param navigateBack Функция для возврата назад. Может быть null.
 * @return Объект [ScreenConfig], содержащий параметры отображения экрана.
 */
fun getScreenConfig(
    screen: NavKey,
    navigateToHistoryExpense: (() -> Unit)? = null,
    navigateToHistoryIncome: (() -> Unit)? = null,
    navigateBack: (() -> Unit)? = null
): ScreenConfig =
    when (screen) {
        MainAppScreen.Expenses -> ScreenConfig(
            title = "Расходы сегодня",
            navigationIcon = { },
            actions = {
                IconButton(onClick = { navigateToHistoryExpense?.invoke() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_history),
                        contentDescription = "История"
                    )
                }
            },
            fabVisibility = true,
            fabOnClick = {},
        )

        MainAppScreen.Income -> ScreenConfig(
            title = "Доходы сегодня",
            navigationIcon = { },
            actions = {
                IconButton(onClick = { navigateToHistoryIncome?.invoke() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_history),
                        contentDescription = "История"
                    )
                }
            },
            fabVisibility = true,
            fabOnClick = {},
        )

        MainAppScreen.Settings -> ScreenConfig(
            title = "Настройки",
            navigationIcon = { },
            actions = {
            }
        )

        MainAppScreen.Articles -> ScreenConfig(
            title = "Мои статьи",
            navigationIcon = { },
            actions = {
            }
        )

        MainAppScreen.Account -> ScreenConfig(
            title = "Мой счет",
            navigationIcon = { },
            actions = {
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = "Редактировать"
                    )
                }
            },
            fabVisibility = true,
            fabOnClick = {},
        )

        HistoryType.Income -> ScreenConfig(
            title = "Моя история",
            navigationIcon = {
                IconButton(onClick = { navigateBack?.invoke() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "назад"
                    )
                }
            },
            actions = {
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_analize_history),
                        contentDescription = "анализ истории"
                    )
                }
            }
        )

        HistoryType.Expenses -> ScreenConfig(
            title = "Моя история",
            navigationIcon = {
                IconButton(onClick = { navigateBack?.invoke() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "назад"
                    )
                }
            },
            actions = {
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_analize_history),
                        contentDescription = "анализ истории"
                    )
                }
            }
        )

        else -> {
            ScreenConfig(
                title = "неопознанный экран"
            )
        }
    }

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
data class ScreenConfig(
    val title: String,
    val navigationIcon: @Composable () -> Unit = {},
    val actions: @Composable RowScope.() -> Unit = {},
    val fabVisibility: Boolean = false,
    val fabOnClick: (() -> Unit)? = null
)