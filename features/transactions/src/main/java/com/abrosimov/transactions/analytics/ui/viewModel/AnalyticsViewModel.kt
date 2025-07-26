package com.abrosimov.transactions.analytics.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abrosimov.gpaphics.analytics_diagram.color_generator.ColorGenerator
import com.abrosimov.gpaphics.analytics_diagram.models.AnalyticsChartData
import com.abrosimov.gpaphics.analytics_diagram.models.AnalyticsChartItem
import com.abrosimov.impl.repository.AccountDetailsRepository
import com.abrosimov.transactions.analytics.domain.GetAnalyticsTransactionsFromPeriodUseCase
import com.abrosimov.transactions.analytics.domain.models.AnalyticsSummary
import com.abrosimov.ui.models.DateRange
import com.abrosimov.ui.navigation.AnalyticsType
import com.abrosimov.utils.dateutils.DateUtils
import com.abrosimov.utils.models.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

class AnalyticsViewModel @Inject constructor(
    private val getAnalyticsUseCase: GetAnalyticsTransactionsFromPeriodUseCase,
    private val accountDetailsRepository: AccountDetailsRepository
) : ViewModel() {
    private val _analyticsState =
        MutableStateFlow<Resource<AnalyticsSummary>>(Resource.Loading)
    val analyticsState: StateFlow<Resource<AnalyticsSummary>> = _analyticsState.asStateFlow()

    private val _chartData = MutableStateFlow<Resource<AnalyticsChartData>>(Resource.Loading)
    val chartData: StateFlow<Resource<AnalyticsChartData>> = _chartData.asStateFlow()

    init {
        viewModelScope.launch {
            _analyticsState.collectLatest { resource ->
                _chartData.value = when (resource) {
                    is Resource.Success -> {
                        ColorGenerator.resetColors()
                        val chartItems = resource.data.items.map { item ->
                            AnalyticsChartItem(
                                name = item.categoryName,
                                percentage = item.percentage,
                                color = ColorGenerator.getRandomColor()
                            )
                        }
                        Resource.Success(AnalyticsChartData(items = chartItems))
                    }
                    is Resource.Error -> Resource.Error(resource.message)
                    Resource.Loading -> Resource.Loading
                }
            }
        }
    }

    fun loadAnalytics(analyticsType: AnalyticsType) {
        viewModelScope.launch {
            val dateRange = _dateRange.value
            val startDateStr = DateUtils.dateToIsoString(dateRange.start)
            val endDateStr = DateUtils.dateToIsoString(dateRange.end)
            _analyticsState.value = getAnalyticsUseCase.getAnalytics(analyticsType, startDateStr, endDateStr)
        }
    }

    fun getCurrency(): String {
        return accountDetailsRepository.getSelectedCurrency()
    }

    private val _dateRange = MutableStateFlow(
        DateRange(
            DateUtils.getStartOfMonth(DateUtils.today()),
            DateUtils.getEndOfDay(DateUtils.today())
        )

    )
    val dateRange: StateFlow<DateRange> get() = _dateRange

    fun updateStartDate(date: Date) {
        _dateRange.update { it.copy(start = date) }
    }

    fun updateEndDate(date: Date) {
        _dateRange.update { it.copy(end = date) }
    }

}