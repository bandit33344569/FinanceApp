package com.abrosimov.financeapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.abrosimov.financeapp.domain.DateUtils.DateUtils
import com.abrosimov.financeapp.domain.repo.Resource
import com.abrosimov.financeapp.ui.FinanceViewModel
import com.abrosimov.financeapp.ui.lists.HistoryExpenseListItem
import com.abrosimov.financeapp.ui.lists.HistoryHeader
import com.abrosimov.financeapp.ui.lists.HistoryIncomeListItem
import com.abrosimov.financeapp.ui.navigation.HistoryType
import com.abrosimov.financeapp.ui.models.ExpensesSummary
import com.abrosimov.financeapp.ui.models.IncomesSummary
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(viewModel: FinanceViewModel, historyType: HistoryType) {
    LaunchedEffect(Unit) {
        Log.d("HistoryScreen", "HistoryScreen created for $historyType")
        viewModel.loadHistoryTransactions()
    }
    val state = when (historyType) {
        HistoryType.Expenses -> viewModel.historyExpensesSummary.collectAsState()
        HistoryType.Income -> viewModel.historyIncomesSummary.collectAsState()
    }
    val dateRange = viewModel.dateRange.collectAsState()
    val displayStartDate = DateUtils.dateToDayMonthTime(dateRange.value.start)
    val displayEndDate = DateUtils.dateToDayMonthTime(dateRange.value.end)

    var showStartDatePicker = rememberSaveable { mutableStateOf(false) }
    var showEndDatePicker = rememberSaveable { mutableStateOf(false) }

    if(showStartDatePicker.value){
        DatePickerModal(
            onDateSelected = {
                viewModel.updateStartDate(Date(it?: 0))
                viewModel.loadHistoryTransactions()
                showStartDatePicker.value = false
            },
            onDismiss = {
                showStartDatePicker.value = false
            }
        )

    }

    if(showEndDatePicker.value){
        DatePickerModal(
            onDateSelected = {
                viewModel.updateEndDate(Date(it?: 0))
                viewModel.loadHistoryTransactions()
                showEndDatePicker.value = false
            },
            onDismiss = {
                showEndDatePicker.value = false
            }
        )

    }

    when (val res = state.value) {
        is Resource.Success -> {
            val summary = res.data
            when (summary) {
                is IncomesSummary -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        HistoryHeader(
                            startDate = displayStartDate,
                            endDate = displayEndDate,
                            summary = "${summary.totalAmount} ${summary.currency}",
                            onStartDateClick = { showStartDatePicker.value = true },
                            onEndDateClick = { showEndDatePicker.value = true }
                        )
                        LazyColumn {
                            items(summary.incomes) { income ->
                                HistoryIncomeListItem(income = income)
                                HorizontalDivider()
                            }
                        }
                    }
                }

                is ExpensesSummary -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        HistoryHeader(
                            startDate = displayStartDate,
                            endDate = displayEndDate,
                            summary = "${summary.totalAmount} ${summary.currency}",
                            onStartDateClick = { showStartDatePicker.value = true },
                            onEndDateClick = { showEndDatePicker.value = true }
                        )
                        LazyColumn {
                            items(summary.expenses) { expense ->
                                HistoryExpenseListItem(expense = expense)
                                HorizontalDivider()
                            }
                        }
                    }
                }
            }
        }

        is Resource.Error -> {
            Column {
                Text("Ошибка: ${res.message}")
                Button(onClick = viewModel::loadHistoryTransactions) {
                    Text("Повторить")
                }
            }
        }

        Resource.Loading -> CircularProgressIndicator()
    }
}