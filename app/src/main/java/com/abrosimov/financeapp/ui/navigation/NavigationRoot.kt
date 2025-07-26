package com.abrosimov.financeapp.ui.navigation

import android.util.Log
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
import androidx.compose.runtime.remember
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
import com.abrosimov.financeapp.ui.navigation.screens.AccountEdit
import com.abrosimov.financeapp.ui.navigation.screens.ColorSelection
import com.abrosimov.financeapp.ui.navigation.screens.MainAppScreen
import com.abrosimov.financeapp.ui.navigation.screens.TransactionEditScreen
import com.abrosimov.impl.viewmodel.SettingsViewModel
import com.abrosimov.settings.presentation.ColorSelectionScreen
import com.abrosimov.settings.presentation.SettingsScreen
import com.abrosimov.transactions.analytics.ui.AnalyticsScreen
import com.abrosimov.transactions.expenses.ui.ExpensesScreen
import com.abrosimov.transactions.history.ui.HistoryScreen
import com.abrosimov.transactions.incomes.ui.IncomeScreen
import com.abrosimov.ui.di.DaggerSharedViewModelComponent
import com.abrosimov.ui.navigation.AnalyticsType
import com.abrosimov.ui.navigation.HistoryType
import com.abrosimov.ui.viewmodel.SharedAppViewModel

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
fun NavigationRoot(settingsViewModel: SettingsViewModel) {
    val sharedViewModelComponent = remember {
        DaggerSharedViewModelComponent.builder().build()
    }
    val sharedAppViewModel =
        viewModel<SharedAppViewModel>(factory = sharedViewModelComponent.sharedAppViewModelFactory)
    val backStack = rememberNavBackStack(MainAppScreen.Account)
    var currentScreen = backStack.lastOrNull() as NavKey
    val screenConfig = when (currentScreen) {
        is HistoryType -> getScreenConfig(
            currentScreen,
            sharedAppViewModel = sharedAppViewModel,
            navigateBack = { backStack.removeLastOrNull() },
            navigateToAnalyticsIncome = {
                Log.d("Navigation", "Переход в аналитику доходов")
                backStack.add(AnalyticsType.Income)
            },
            navigateToAnalyticsExpense = {
                Log.d("Navigation", "Переход в аналитику расходов")
                backStack.add(AnalyticsType.Expenses)
            },
        )

        else -> getScreenConfig(
            currentScreen,
            navigateToHistoryExpense = { backStack.add(HistoryType.Expenses) },
            navigateToHistoryIncome = { backStack.add(HistoryType.Income) },
            onNavigateToAccountEdit = { backStack.add(AccountEdit) },
            navigateBack = { backStack.removeLastOrNull() },
            fabOnClick = { backStack.add(TransactionEditScreen(id = null)) },
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
                    ExpensesScreen(
                        onTransactionClick = { transactionId ->
                            backStack.add(TransactionEditScreen(transactionId))
                        }
                    )
                }

                entry<MainAppScreen.Income> {
                    IncomeScreen(
                        onTransactionClick = { transactionId ->
                            backStack.add(TransactionEditScreen(transactionId))
                        })
                }

                entry<MainAppScreen.Account> {
                    AccountScreen()
                }

                entry<MainAppScreen.Articles> {
                    CategoryScreen()
                }

                entry<MainAppScreen.Settings> {
                    SettingsScreen(
                        viewModel = settingsViewModel,
                        navigateToChangeColorScreen = { backStack.add(ColorSelection) }
                    )
                }

                entry<ColorSelection>{
                    ColorSelectionScreen(settingsViewModel)
                }

                entry<HistoryType.Expenses> {
                    HistoryScreen(
                        historyType = HistoryType.Expenses,
                        onTransactionClick = { transactionId ->
                            backStack.add(TransactionEditScreen(transactionId))
                        }
                    )
                }
                entry<HistoryType.Income> {
                    HistoryScreen(
                        historyType = HistoryType.Income,
                        onTransactionClick = { transactionId ->
                            backStack.add(TransactionEditScreen(transactionId))
                        }
                    )
                }

                entry<AnalyticsType.Expenses> {
                    AnalyticsScreen(AnalyticsType.Expenses)
                }

                entry<AnalyticsType.Income> {
                    AnalyticsScreen(AnalyticsType.Income)
                }
                entry<AccountEdit> {
                    AccountEditScreen()
                }
                entry<TransactionEditScreen> { key ->
                    val transactionId = key.id
                    com.abrosimov.transactions.edit_transaction.ui.TransactionEditScreen(id = transactionId)
                }
            }
        )
    }

}

