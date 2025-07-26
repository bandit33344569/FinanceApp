package com.abrosimov.transactions.analytics.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abrosimov.gpaphics.analytics_diagram.composable.DonutChart
import com.abrosimov.transactions.analytics.di.DaggerAnalyticsComponent
import com.abrosimov.ui.navigation.AnalyticsType
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
        viewModel.loadAnalytics(analyticsType)
    }
    val state = viewModel.analyticsState.collectAsStateWithLifecycle()
    val currency = viewModel.getCurrency()
    val dateRange = viewModel.dateRange.collectAsStateWithLifecycle()
    val displayStartDate = DateUtils.dateToDayMonthTime(dateRange.value.start)
    val displayEndDate = DateUtils.dateToDayMonthTime(dateRange.value.end)
    val chartState = viewModel.chartData.collectAsStateWithLifecycle()
    val showStartDatePicker = rememberSaveable { mutableStateOf(false) }
    val showEndDatePicker = rememberSaveable { mutableStateOf(false) }

    if (showStartDatePicker.value) {
        DatePickerModal(
            onDateSelected = {
                viewModel.updateStartDate(Date(it ?: 0))
                viewModel.loadAnalytics(analyticsType)
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
                viewModel.loadAnalytics(analyticsType)
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
            Column(modifier = Modifier.fillMaxSize()) {
                AnalyticsHeader(
                    startDate = displayStartDate,
                    endDate = displayEndDate,
                    summary = "${summary.totalAmount} $currency",
                    onStartDateClick = { showStartDatePicker.value = true },
                    onEndDateClick = { showEndDatePicker.value = true }
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn {
                    item {
                        when (val chartRes = chartState.value) {
                            is Resource.Success -> {
                                DonutChart(
                                    chartData = chartRes.data,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(250.dp)
                                        .padding(horizontal = 16.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                HorizontalDivider()
                            }
                            is Resource.Error -> {
                                Text(
                                    text = "Ошибка загрузки графика: ${chartRes.message}",
                                    modifier = Modifier.padding(16.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                HorizontalDivider()
                            }
                            Resource.Loading -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(250.dp)
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                HorizontalDivider()
                            }
                        }
                    }
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

        is Resource.Error -> {
            Column {
                Text("Ошибка: ${res.message}")
                Button(onClick = { viewModel.loadAnalytics(analyticsType) }) {
                    Text("Повторить")
                }
            }
        }

        Resource.Loading -> CircularProgressIndicator()
    }
}
