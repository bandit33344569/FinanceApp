package com.abrosimov.financeapp.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.abrosimov.financeapp.R
import com.abrosimov.financeapp.ui.screens.ArticlesScreen
import com.abrosimov.financeapp.ui.screens.AccountScreen
import com.abrosimov.financeapp.ui.screens.ExpensesScreen
import com.abrosimov.financeapp.ui.screens.IncomeScreen
import com.abrosimov.financeapp.ui.screens.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(AppScreen.Expenses)
    val currentScreen = backStack.lastOrNull() as AppScreen? ?: AppScreen.Expenses
    val screenConfig = getScreenConfig(currentScreen)

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentScreen = currentScreen,
                onNavigate = { screen ->
                    if (currentScreen != screen) {
                        backStack.add(screen)
                    }
                }
            )
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(screenConfig.title) },
                navigationIcon = screenConfig.navigationIcon,
                actions = screenConfig.actions,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
            )
        },
        floatingActionButton = {
            if (screenConfig.fabVisibility && screenConfig.fabOnClick != null) {
                FloatingActionButton(
                    onClick = screenConfig.fabOnClick,
                    shape = CircleShape,
                    content = {
                        Icon(Icons.Filled.Add, "Добавить")
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.background
                )
            }
        }
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier.padding(innerPadding),
            backStack = backStack,
            entryProvider = entryProvider {
                entry<AppScreen.Expenses> {
                    ExpensesScreen()
                }

                entry<AppScreen.Income> {
                    IncomeScreen()
                }

                entry<AppScreen.Account> {
                    AccountScreen()
                }

                entry<AppScreen.Articles> {
                    ArticlesScreen()
                }

                entry<AppScreen.Settings> {
                    SettingsScreen()
                }
            }
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