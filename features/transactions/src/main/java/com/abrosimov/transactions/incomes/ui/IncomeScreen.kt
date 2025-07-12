package com.abrosimov.transactions.incomes.ui


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
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abrosimov.transactions.di.TransactionDependenciesStore
import com.abrosimov.transactions.incomes.di.DaggerIncomesComponent
import com.abrosimov.utils.models.Resource


@Composable
fun IncomeScreen(
    onTransactionClick: (Int?) -> (Unit)
) {
    val incomeComponent = remember {
        DaggerIncomesComponent.builder()
            .dependencies(transactionsDependencies = TransactionDependenciesStore.transactionsDependencies)
            .build()
    }
    val viewModel = viewModel<IncomesViewModel>(factory = incomeComponent.incomesViewModelFactory)
    LaunchedEffect(Unit) { viewModel.loadTodayIncomes() }
    val state = viewModel.todayIncomesSummary.collectAsState()
    val currency = viewModel.getCurrency()
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
            Column {
                IncomeHeader("$totalAmount $currency")
                LazyColumn {
                    items(incomes) { income ->
                        IncomeListItem(
                            income,
                            onDetailClick = { onTransactionClick(income.id) }
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}