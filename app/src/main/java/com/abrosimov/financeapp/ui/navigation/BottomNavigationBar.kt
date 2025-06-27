package com.abrosimov.financeapp.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation3.runtime.NavKey
import com.abrosimov.financeapp.R

/**
 * Компонент нижней панели навигации, отображающий несколько пунктов меню.
 *
 * Позволяет пользователю перемещаться между основными экранами приложения: Расходы, Доходы,
 * Счет, Статьи и Настройки. Использует [NavigationBar] из Material 3.
 *
 * @param currentScreen Текущий активный экран (для подсветки выбранного элемента).
 * @param onNavigate Функция обратного вызова, вызываемая при выборе нового экрана.
 */
@Composable
fun BottomNavigationBar(
    currentScreen: NavKey,
    onNavigate: (NavKey) -> Unit
) {
    NavigationBar(
        modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars),
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    ) {
        val screens = listOf(
            MainAppScreen.Expenses,
            MainAppScreen.Income,
            MainAppScreen.Account,
            MainAppScreen.Articles,
            MainAppScreen.Settings
        )

        screens.forEach { screen: MainAppScreen ->
            val iconResId = when (screen) {
                MainAppScreen.Expenses -> R.drawable.ic_expenses
                MainAppScreen.Income -> R.drawable.ic_income
                MainAppScreen.Account -> R.drawable.ic_check
                MainAppScreen.Articles -> R.drawable.ic_articles
                MainAppScreen.Settings -> R.drawable.ic_settings
            }

            val label = when (screen) {
                MainAppScreen.Expenses -> "Расходы"
                MainAppScreen.Income -> "Доходы"
                MainAppScreen.Account -> "Счет"
                MainAppScreen.Articles -> "Статьи"
                MainAppScreen.Settings -> "Настройки"
            }

            AddNavigationItem(
                screen = screen,
                currentScreen = currentScreen,
                onNavigate = onNavigate,
                iconResId = iconResId,
                label = label,
            )
        }
    }
}

/**
 * Отдельный элемент нижней панели навигации, представляющий один экран приложения.
 *
 * Отображает иконку и текстовый заголовок, меняя цвета в зависимости от того,
 * является ли элемент текущим активным экраном.
 *
 * @param screen Экран, связанный с этим элементом навигации.
 * @param currentScreen Текущий активный экран приложения.
 * @param onNavigate Функция обратного вызова, вызываемая при нажатии на элемент.
 * @param iconResId Ресурс иконки, отображаемой для этого элемента.
 * @param label Текстовая метка, отображаемая под иконкой.
 * @param colors Цвета элемента, зависящие от состояния (выбран/не выбран/отключен).
 */
@Composable
fun RowScope.AddNavigationItem(
    screen: NavKey,
    currentScreen: NavKey,
    onNavigate: (NavKey) -> Unit,
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