package com.abrosimov.financeapp.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation3.runtime.NavKey
import com.abrosimov.financeapp.R

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

data class ScreenConfig(
    val title: String,
    val navigationIcon: @Composable () -> Unit = {},
    val actions: @Composable RowScope.() -> Unit = {},
    val fabVisibility: Boolean = false,
    val fabOnClick: (() -> Unit)? = null
)