package com.abrosimov.financeapp.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.core.graphics.toColor
import com.abrosimov.financeapp.R

@Composable
fun BottomNavigationBar(
    currentScreen: AppScreen,
    onNavigate: (AppScreen) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) {
        val screens = listOf(
            AppScreen.Expenses,
            AppScreen.Income,
            AppScreen.Check,
            AppScreen.Articles,
            AppScreen.Settings
        )

        screens.forEach { screen ->
            val iconResId = when (screen) {
                AppScreen.Expenses -> R.drawable.ic_expenses
                AppScreen.Income -> R.drawable.ic_income
                AppScreen.Check -> R.drawable.ic_check
                AppScreen.Articles -> R.drawable.ic_articles
                AppScreen.Settings -> R.drawable.ic_settings
            }

            val label = when (screen) {
                AppScreen.Expenses -> "Расходы"
                AppScreen.Income -> "Доходы"
                AppScreen.Check -> "Счет"
                AppScreen.Articles -> "Статьи"
                AppScreen.Settings -> "Настройки"
            }

            AddNavigationItem(
                screen = screen,
                currentScreen = currentScreen,
                onNavigate = onNavigate,
                iconResId = iconResId,
                label = label
            )
        }
    }
}

/**
 * Отдельный элемент нижней панели навигации
 *
 * @param screen Экран, связанный с этим элементом
 * @param currentScreen Текущий экран приложения
 * @param onNavigate Функция для перехода
 * @param iconResId Иконка элемента
 * @param label Заголовок элемента
 */
@Composable
fun RowScope.AddNavigationItem(
    screen: AppScreen,
    currentScreen: AppScreen,
    onNavigate: (AppScreen) -> Unit,
    iconResId: Int,
    label: String,
    colors: NavigationBarItemColors = NavigationBarItemColors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        selectedTextColor = MaterialTheme.colorScheme.onSurface,
        selectedIndicatorColor = MaterialTheme.colorScheme.secondary,
        unselectedIconColor = MaterialTheme.colorScheme.tertiary,
        unselectedTextColor = MaterialTheme.colorScheme.tertiary,
        disabledIconColor = MaterialTheme.colorScheme.tertiary,
        disabledTextColor = MaterialTheme.colorScheme.tertiary
    )
) {
    val selected = currentScreen == screen

    NavigationBarItem(
        icon = {
            Icon(
                painterResource(id = iconResId),
                contentDescription = null,
            )
        },
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium
            )
        },
        selected = selected,
        colors = colors,
        onClick = {
            if (!selected) {
                onNavigate(screen)
            }
        }
    )
}