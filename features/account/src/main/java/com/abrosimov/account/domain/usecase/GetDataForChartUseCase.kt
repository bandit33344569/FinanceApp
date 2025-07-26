package com.abrosimov.account.domain.usecase

import com.abrosimov.api.repository.TransactionRepository
import com.abrosimov.gpaphics.month_chart.models.BarData
import com.abrosimov.gpaphics.month_chart.models.BarType
import com.abrosimov.gpaphics.month_chart.models.ChartData
import com.abrosimov.utils.dateutils.DateUtils
import com.abrosimov.utils.models.Resource
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import javax.inject.Inject

class GetDataForChartUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(
        accountId: Int,
        startDate: String? = DateUtils.dateToIsoString(DateUtils.getStartOfMonth(Date())),
        endDate: String? = DateUtils.dateToIsoString(DateUtils.getEndOfMonth(Date()))
    ): Resource<ChartData> {
        val formatter = DateTimeFormatter.ofPattern("dd.MM")
        val isoFormatter = DateTimeFormatter.ISO_LOCAL_DATE
        return when (val transactionsResource = transactionRepository.getTransactionFromPeriod(accountId, startDate, endDate)) {
            is Resource.Success -> {
                val transactions = transactionsResource.data


                val start = try {
                    DateUtils.isoStringToDate(
                        startDate ?: DateUtils.dateToIsoString(DateUtils.getStartOfMonth(Date()))
                    ).toInstant().atZone(ZoneId.of("UTC")).toLocalDate()
                } catch (e: Exception) {
                    return Resource.Error("Ошибка парсинга startDate: ${e.message}")
                }
                val end = try {
                    DateUtils.isoStringToDate(
                        endDate ?: DateUtils.dateToIsoString(DateUtils.getEndOfMonth(Date()))
                    ).toInstant().atZone(ZoneId.of("UTC")).toLocalDate()
                } catch (e: Exception) {
                    return Resource.Error("Ошибка парсинга endDate: ${e.message}")
                }

                val transactionsByDate = transactions.groupBy { transaction ->
                    LocalDate.parse(DateUtils.getDateStringFromIso(transaction.transactionDate), isoFormatter).format(formatter)
                }

                val bars = mutableListOf<BarData>()
                var currentDate = start
                while (!currentDate.isAfter(end)) {
                    val dateStr = currentDate.format(formatter)

                    val totalAmount = transactionsByDate[dateStr]?.sumOf { transaction ->
                        val amount = transaction.amount.toFloatOrNull()?.toDouble() ?: 0.0
                        if (transaction.category.isIncome) amount else -amount
                    }?.toFloat() ?: 0f

                    val type = when {
                        totalAmount > 0 -> BarType.Income
                        totalAmount < 0 -> BarType.Expense
                        else -> BarType.Zero
                    }

                    bars.add(
                        BarData(
                            date = dateStr,
                            value = totalAmount,
                            type = type
                        )
                    )
                    currentDate = currentDate.plusDays(1)
                }

                Resource.Success(
                    ChartData(
                        bars = bars,
                        labelFrequency = 10
                    )
                )
            }
            is Resource.Error -> transactionsResource
            is Resource.Loading -> Resource.Loading
        }
    }
}