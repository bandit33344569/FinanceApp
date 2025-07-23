package com.abrosimov.transactions.analytics.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abrosimov.transactions.analytics.di.DaggerAnalyticsComponent
import com.abrosimov.ui.navigation.AnalyticsType
import com.abrosimov.transactions.analytics.domain.models.ExpenseAnalyticsSummary
import com.abrosimov.transactions.analytics.domain.models.IncomeAnalyticsSummary
import com.abrosimov.transactions.analytics.ui.items.AnalyticsHeader
import com.abrosimov.transactions.analytics.ui.items.AnalyticsListItem
import com.abrosimov.transactions.analytics.ui.viewModel.AnalyticsViewModel
import com.abrosimov.transactions.di.TransactionDependenciesStore
import com.abrosimov.ui.composableFunctions.DatePickerModal
import com.abrosimov.utils.dateutils.DateUtils
import com.abrosimov.utils.models.Resource
import java.util.Date

@Composable
fun AnalyticsScreen(
    analyticsType: AnalyticsType
) {
    val analyticsComponent = remember {
        DaggerAnalyticsComponent.builder()
            .dependencies(transactionsDependencies = TransactionDependenciesStore.transactionsDependencies)
            .build()
    }
    val viewModel =
        viewModel<AnalyticsViewModel>(factory = analyticsComponent.analyticsViewModelFactory)
    LaunchedEffect(Unit) {
        Log.d("AnalyticsScreen", "AnalyticsScreen created for $analyticsType")
        viewModel.loadAnalytics()
    }
    val state = when (analyticsType) {
        AnalyticsType.Expenses -> viewModel.expenseState.collectAsStateWithLifecycle()
        AnalyticsType.Income -> viewModel.incomeState.collectAsStateWithLifecycle()
    }
    val currency = viewModel.getCurrency()
    val dateRange = viewModel.dateRange.collectAsStateWithLifecycle()
    val displayStartDate = DateUtils.dateToDayMonthTime(dateRange.value.start)
    val displayEndDate = DateUtils.dateToDayMonthTime(dateRange.value.end)

    val showStartDatePicker = rememberSaveable { mutableStateOf(false) }
    val showEndDatePicker = rememberSaveable { mutableStateOf(false) }

    if (showStartDatePicker.value) {
        DatePickerModal(
            onDateSelected = {
                viewModel.updateStartDate(Date(it ?: 0))
                viewModel.loadAnalytics()
                showStartDatePicker.value = false
            },
            onDismiss = {
                showStartDatePicker.value = false
            }
        )

    }

    if (showEndDatePicker.value) {
        DatePickerModal(
            onDateSelected = {
                viewModel.updateEndDate(Date(it ?: 0))
                viewModel.loadAnalytics()
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
                is ExpenseAnalyticsSummary -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        AnalyticsHeader(
                            startDate = displayStartDate,
                            endDate = displayEndDate,
                            summary = "${summary.totalAmount} $currency",
                            onStartDateClick = { showStartDatePicker.value = true },
                            onEndDateClick = { showEndDatePicker.value = true }
                        )
                        LazyColumn {
                            items(summary.items) {
                                AnalyticsListItem(
                                    currency = currency,
                                    categoryAnalyticsItem = it
                                )
                                HorizontalDivider()
                            }
                        }
                    }
                }

                is IncomeAnalyticsSummary -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        AnalyticsHeader(
                            startDate = displayStartDate,
                            endDate = displayEndDate,
                            summary = "${summary.totalAmount} $currency",
                            onStartDateClick = { showStartDatePicker.value = true },
                            onEndDateClick = { showEndDatePicker.value = true }
                        )
                        LazyColumn {
                            items(summary.items) {
                                AnalyticsListItem(
                                    currency = currency,
                                    categoryAnalyticsItem = it
                                )
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
                Button(onClick = viewModel::loadAnalytics) {
                    Text("Повторить")
                }
            }
        }

        Resource.Loading -> CircularProgressIndicator()
    }
}
