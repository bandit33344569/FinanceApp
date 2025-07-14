package com.abrosimov.transactions.expenses.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abrosimov.transactions.di.TransactionDependenciesStore
import com.abrosimov.transactions.expenses.di.DaggerExpensesComponent
import com.abrosimov.utils.models.Resource


@Composable
fun ExpensesScreen(
    onTransactionClick: (Int?) -> (Unit)
) {
    val expensesComponent = remember {
        DaggerExpensesComponent
            .builder()
            .dependencies(transactionsDependencies = TransactionDependenciesStore.transactionsDependencies)
            .build()
    }
    val viewModel =
        viewModel<ExpensesViewModel>(factory = expensesComponent.expensesViewModelFactory)

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

        is Resource.Loading -> CircularProgressIndicator()
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
                            onClick = {
                                onTransactionClick(expense.id)
                                Log.d("ExpenseListItem", "clicked ${expense.id}")
                            }
                        )
                        HorizontalDivider()
                    }
                }

            }
        }
    }
}