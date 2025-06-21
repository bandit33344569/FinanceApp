package com.abrosimov.financeapp.ui.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.abrosimov.financeapp.domain.repo.Resource
import com.abrosimov.financeapp.ui.FinanceViewModel
import com.abrosimov.financeapp.ui.lists.ExpensesAndIncomeHeader
import com.abrosimov.financeapp.ui.lists.IncomeListItem
import com.abrosimov.financeapp.ui.models.Income

@Composable
fun IncomeScreen(viewModel: FinanceViewModel) {
    viewModel.loadTodayTransactions()
    val state = viewModel.todayIncomesSummary.collectAsState()
    when (val res = state.value) {
        is Resource.Error -> {
            Column {
                Text("Ошибка: ${(state.value as Resource.Error).message}")
                Button(onClick = viewModel::loadTodayTransactions) {
                    Text("Повторить")
                }
            }
        }

        Resource.Loading -> CircularProgressIndicator()
        is Resource.Success -> {
            val summary = res.data
            val incomes = summary.incomes
            val totalAmount = summary.totalAmount
            val currency = summary.currency
            Column {
                ExpensesAndIncomeHeader("$totalAmount $currency")
                LazyColumn {
                    items(incomes) { income ->
                        IncomeListItem(
                            income,
                            onDetailClick = {}
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}