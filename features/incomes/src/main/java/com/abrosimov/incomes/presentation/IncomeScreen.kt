package com.abrosimov.incomes.presentation


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abrosimov.core.di.LocalViewModelFactory
import com.abrosimov.core.domain.Resource


@Composable
fun IncomeScreen(viewModel: IncomesViewModel = viewModel(factory = LocalViewModelFactory.current)) {
    LaunchedEffect(Unit) { viewModel.loadTodayIncomes()}
    val state = viewModel.todayIncomesSummary.collectAsState()
    when (val res = state.value) {
        is Resource.Error -> {
            Column {
                Text("Ошибка: ${(state.value as Resource.Error).message}")
                Button(onClick = viewModel::loadTodayIncomes) {
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
                IncomeHeader("$totalAmount $currency")
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