package com.abrosimov.expenses.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abrosimov.core.di.LocalViewModelFactory
import com.abrosimov.core.domain.Resource
import com.abrosimov.expenses.presentation.common.ExpenseHeader
import com.abrosimov.expenses.presentation.common.ExpenseListItem
import com.abrosimov.expenses.presentation.viewmodel.ExpensesViewModel


@Composable
fun ExpensesScreen(
    viewModel: ExpensesViewModel = viewModel(factory = LocalViewModelFactory.current),
) {
    LaunchedEffect(Unit) { viewModel.loadTodayExpenses() }
    val state = viewModel.todayExpensesSummary.collectAsStateWithLifecycle()
    val currency = viewModel.getCurrency()
    when (val res = state.value) {
        is Resource.Error -> {
            Column {
                Text("Ошибка: ${(state.value as Resource.Error).message}")
                Button(onClick = viewModel::loadTodayExpenses) {
                    Text("Повторить")
                }
            }
        }

        Resource.Loading -> CircularProgressIndicator()
        is Resource.Success -> {
            val summary = res.data
            val expenses = summary.expenses
            val totalAmount = summary.totalAmount
            Column {
                ExpenseHeader("$totalAmount $currency")
                LazyColumn {
                    items(expenses) { expense ->
                        ExpenseListItem(
                            expense = expense,
                        )
                        HorizontalDivider()
                    }
                }

            }
        }
    }
}