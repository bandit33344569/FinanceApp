package com.abrosimov.financeapp.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.abrosimov.financeapp.R

fun getScreenConfig(screen: AppScreen): ScreenConfig = when (screen) {
    AppScreen.Expenses -> ScreenConfig(
        title = "Расходы сегодня",
        navigationIcon = { },
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_history),
                    contentDescription = "История"
                )
            }
        },
        fabVisibility = true,
        fabOnClick = {},
    )

    AppScreen.Income -> ScreenConfig(
        title = "Доходы сегодня",
        navigationIcon = { },
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_history),
                    contentDescription = "История"
                )
            }
        },
        fabVisibility = true,
        fabOnClick = {},
    )

    AppScreen.Settings -> ScreenConfig(
        title = "Настройки",
        navigationIcon = { },
        actions = {
        }
    )

    AppScreen.Articles -> ScreenConfig(
        title = "Мои статьи",
        navigationIcon = { },
        actions = {
        }
    )

    AppScreen.Account -> ScreenConfig(
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
}

data class ScreenConfig(
    val title: String,
    val navigationIcon: @Composable () -> Unit = {},
    val actions: @Composable RowScope.() -> Unit = {},
    val fabVisibility: Boolean = false,
    val fabOnClick: (() -> Unit)? = null
)