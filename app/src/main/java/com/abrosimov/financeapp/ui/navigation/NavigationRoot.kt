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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.abrosimov.financeapp.ui.FinanceViewModel
import com.abrosimov.financeapp.ui.screens.CategoryScreen
import com.abrosimov.financeapp.ui.screens.AccountScreen
import com.abrosimov.financeapp.ui.screens.ExpensesScreen
import com.abrosimov.financeapp.ui.screens.HistoryScreen
import com.abrosimov.financeapp.ui.screens.IncomeScreen
import com.abrosimov.financeapp.ui.screens.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(MainAppScreen.Expenses)
    var currentScreen = backStack.lastOrNull() as MainAppScreen? ?: MainAppScreen.Expenses
    val screenConfig = getScreenConfig(currentScreen)
    val viewModel: FinanceViewModel  = hiltViewModel()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentScreen = currentScreen,
                onNavigate = { screen ->
                    if (backStack.lastOrNull() != screen) {
                        Log.i("backstack","$backStack")
                        if (screen in backStack) {
                            val newStack = backStack.toMutableList()
                            newStack.remove(screen)
                            newStack.add(screen)
                            backStack.clear()
                            newStack.forEach { backStack.add(it) }
                        } else {
                            backStack.add(screen)
                        }
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
            onBack = {backStack.removeLastOrNull()},
            entryProvider = entryProvider {
                entry<MainAppScreen.Expenses> {
                    ExpensesScreen(viewModel)
                }

                entry<MainAppScreen.Income> {
                    IncomeScreen(viewModel)
                }

                entry<MainAppScreen.Account> {
                    AccountScreen(viewModel)
                }

                entry<MainAppScreen.Articles> {
                    CategoryScreen(viewModel)
                }

                entry<MainAppScreen.Settings> {
                    SettingsScreen()
                }

                entry <History>{
                    HistoryScreen()
                }
            }
        )
    }

}

