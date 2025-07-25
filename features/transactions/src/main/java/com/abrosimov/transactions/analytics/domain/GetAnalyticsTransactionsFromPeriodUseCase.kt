package com.abrosimov.transactions.analytics.domain

import com.abrosimov.api.models.dto.SpecTransactionDto
import com.abrosimov.api.repository.TransactionRepository
import com.abrosimov.impl.repository.AccountDetailsRepository
import com.abrosimov.transactions.analytics.domain.models.AnalyticsSummary
import com.abrosimov.transactions.analytics.domain.models.CategoryAnalyticsItem
import com.abrosimov.ui.navigation.AnalyticsType
import com.abrosimov.utils.models.Resource
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject


class GetAnalyticsTransactionsFromPeriodUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val accountDetailsRepository: AccountDetailsRepository
) {
    suspend fun getAnalytics(
        analyticsType: AnalyticsType,
        startDate: String? = null,
        endDate: String? = null
    ): Resource<AnalyticsSummary> {
        return getAnalyticsData(
            filter = { transaction -> transaction.category.isIncome == (analyticsType == AnalyticsType.Income) },
            startDate = startDate,
            endDate = endDate
        )
    }

    private suspend fun getAnalyticsData(
        filter: (SpecTransactionDto) -> Boolean,
        startDate: String?,
        endDate: String?
    ): Resource<AnalyticsSummary> {
        val accountId = accountDetailsRepository.getAccountId()

        return when (val result =
            transactionRepository.getTransactionFromPeriod(accountId, startDate, endDate)) {
            is Resource.Success -> {
                val transactions = result.data.filter(filter)

                if (transactions.isEmpty()) {
                    return Resource.Success(AnalyticsSummary(BigDecimal.ZERO, emptyList()))
                }

                val totalAmount = transactions
                    .map { it.amount.toBigDecimal() }
                    .sumOf { it }

                val grouped = transactions.groupBy { it.category }
                val items = grouped.map { (category, transactions) ->
                    val sum = transactions.map { it.amount.toBigDecimal() }.sumOf { it }
                    val percentage = if (totalAmount > BigDecimal.ZERO) {
                        (sum / totalAmount * BigDecimal(100)).toDouble()
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

                val adjustedItems = adjustPercentages(items)

                Resource.Success(AnalyticsSummary(totalAmount, adjustedItems))
            }

            is Resource.Error -> Resource.Error(result.message)
            Resource.Loading -> Resource.Loading
        }
    }

    private fun adjustPercentages(items: List<CategoryAnalyticsItem>): List<CategoryAnalyticsItem> {
        if (items.isEmpty()) return items

        val totalPercentage = items.sumOf { it.percentage }
        if (totalPercentage == 0.0) return items
        val scaleFactor = 100.0 / totalPercentage
        val adjustedItems = items.map { item ->
            val adjustedPercentage = if (item.percentage > 0) {
                BigDecimal(item.percentage * scaleFactor)
                    .setScale(2, RoundingMode.HALF_UP)
                    .toDouble()
            } else 0.0
            item.copy(percentage = adjustedPercentage)
        }

        val finalSum = adjustedItems.sumOf { it.percentage }
        return if (finalSum != 100.0 && adjustedItems.isNotEmpty()) {
            val lastItem = adjustedItems.last()
            val adjustedLastPercentage = (lastItem.percentage + (100.0 - finalSum))
                .coerceAtLeast(0.0)
                .let { BigDecimal(it).setScale(2, RoundingMode.HALF_UP).toDouble() }
            adjustedItems.dropLast(1) + lastItem.copy(percentage = adjustedLastPercentage)
        } else {
            adjustedItems
        }
    }
}