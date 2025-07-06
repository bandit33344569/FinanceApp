package com.abrosimov.financeapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.abrosimov.account.presentation.screens.AccountEditScreen
import com.abrosimov.account.presentation.screens.AccountScreen
import com.abrosimov.categories.presentation.CategoryScreen
import com.abrosimov.core.di.LocalViewModelFactory
import com.abrosimov.core.presentation.navigation.HistoryType
import com.abrosimov.core.presentation.viewmodel.SharedAppViewModel
import com.abrosimov.expenses.presentation.screen.ExpensesScreen
import com.abrosimov.financeapp.ui.navigation.screens.AccountEdit
import com.abrosimov.financeapp.ui.navigation.screens.MainAppScreen
import com.abrosimov.history.presentation.screen.HistoryScreen
import com.abrosimov.incomes.presentation.IncomeScreen
import com.abrosimov.settings.presentation.SettingsScreen

/**
 * Основной навигационный компонент приложения, управляющий переходами между экранами.
 *
 * Реализует полный цикл навигации с поддержкой:
 * - Нижней панели навигации (Bottom Navigation)
 * - Верхней панели с динамическим заголовком и действиями
 * - Floating Action Button (FAB) — отображается только при необходимости
 * - Поддержка вложенных экранов (например, "История")
 *
 * Использует [androidx.navigation3] — новую систему навигации Jetpack Compose.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationRoot(sharedAppViewModel: SharedAppViewModel = viewModel(factory = LocalViewModelFactory.current,key = "SharedAppViewModel")) {
    val backStack = rememberNavBackStack(MainAppScreen.Account)
    var currentScreen = backStack.lastOrNull() as NavKey
    val screenConfig = when (currentScreen) {
        is HistoryType -> getScreenConfig(
            currentScreen,
            sharedAppViewModel = sharedAppViewModel,
            navigateBack = { backStack.removeLastOrNull() })

        else -> getScreenConfig(
            currentScreen,
            navigateToHistoryExpense = { backStack.add(HistoryType.Expenses) },
            navigateToHistoryIncome = { backStack.add(HistoryType.Income) },
            onNavigateToAccountEdit = { backStack.add(AccountEdit) },
            navigateBack = { backStack.removeLastOrNull() },
            sharedAppViewModel = sharedAppViewModel,
        )
    }
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentScreen = currentScreen,
                onNavigate = { screen ->
                    if (screen in backStack) {
                        backStack.removeAll { it == screen }
                    }
                    backStack.add(screen)
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
                    contentColor = MaterialTheme.colorScheme.background,
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp
                    )
                )
            }
        }
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier.padding(innerPadding),
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<MainAppScreen.Expenses> {
                    ExpensesScreen()
                }

                entry<MainAppScreen.Income> {
                    IncomeScreen()
                }

                entry<MainAppScreen.Account> {
                    AccountScreen()
                }

                entry<MainAppScreen.Articles> {
                    CategoryScreen()
                }

                entry<MainAppScreen.Settings> {
                    SettingsScreen()
                }

                entry<HistoryType.Expenses> {
                    HistoryScreen(
                        historyType = HistoryType.Expenses
                    )
                }
                entry<HistoryType.Income> {
                    HistoryScreen(historyType = HistoryType.Income)
                }
                entry<AccountEdit> {
                    AccountEditScreen(sharedAppViewModel)
                }
            }
        )
    }

}

