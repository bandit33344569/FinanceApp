package com.abrosimov.transactions.analytics.domain

import com.abrosimov.api.models.dto.SpecTransactionDto
import com.abrosimov.api.repository.TransactionRepository
import com.abrosimov.impl.repository.AccountDetailsRepository
import com.abrosimov.transactions.analytics.domain.models.AnalyticsSummary
import com.abrosimov.transactions.analytics.domain.models.CategoryAnalyticsItem
import com.abrosimov.transactions.analytics.domain.models.ExpenseAnalyticsSummary
import com.abrosimov.transactions.analytics.domain.models.IncomeAnalyticsSummary
import com.abrosimov.utils.models.Resource
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject


class GetAnalyticsTransactionsFromPeriodUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val accountDetailsRepository: AccountDetailsRepository
) {
    suspend fun getIncomeAnalytics(
        startDate: String? = null,
        endDate: String? = null
    ): Resource<IncomeAnalyticsSummary> {
        return getAnalyticsData(
            filter = { it.category.isIncome },
            startDate = startDate,
            endDate = endDate,
            summaryFactory = ::IncomeAnalyticsSummary
        )
    }

    suspend fun getExpenseAnalytics(
        startDate: String? = null,
        endDate: String? = null
    ): Resource<ExpenseAnalyticsSummary> {
        return getAnalyticsData(
            filter = { !it.category.isIncome },
            startDate = startDate,
            endDate = endDate,
            summaryFactory = ::ExpenseAnalyticsSummary
        )
    }

    private suspend fun <T : AnalyticsSummary> getAnalyticsData(
        filter: (SpecTransactionDto) -> Boolean,
        startDate: String?,
        endDate: String?,
        summaryFactory: (BigDecimal, List<CategoryAnalyticsItem>) -> T
    ): Resource<T> {
        val accountId = accountDetailsRepository.getAccountId()

        return when (val result =
            transactionRepository.getTransactionFromPeriod(accountId, startDate, endDate)) {
            is Resource.Success -> {
                val transactions = result.data.filter(filter)

                if (transactions.isEmpty()) {
                    return Resource.Success(summaryFactory(BigDecimal.ZERO, emptyList()))
                }

                val totalAmount = transactions
                    .map { it.amount.toBigDecimal() }
                    .sumOf { it }

                val grouped = transactions.groupBy { it.category }
                val items = grouped.map { (category, transactions) ->
                    val sum =
                        transactions.map { it.amount.toBigDecimal() }.sumOf { it }
                    val percentage = if (totalAmount > BigDecimal.ZERO) {
                        (sum / totalAmount * BigDecimal(100)).setScale(2, RoundingMode.HALF_UP)
                            .toDouble()
                    } else 0.0

                    CategoryAnalyticsItem(
                        categoryId = category.id,
                        categoryName = category.name,
                        categoryEmoji = category.emoji,
                        totalAmount = sum,
                        percentage = percentage,
                        isIncome = filter(transactions.first())
                    )
                }.sortedByDescending { it.totalAmount }
                Resource.Success(summaryFactory(totalAmount, items))
            }

            is Resource.Error -> Resource.Error(result.message)
            Resource.Loading -> Resource.Loading
        }
    }
}