package com.abrosimov.transactions.analytics.ui.items

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.abrosimov.transactions.R
import com.abrosimov.transactions.analytics.domain.models.CategoryAnalyticsItem
import com.abrosimov.ui.composableFunctions.CustomListItem

@Composable
fun AnalyticsListItem(
    currency: String,
    categoryAnalyticsItem: CategoryAnalyticsItem
){
    CustomListItem(
        leftTitle = categoryAnalyticsItem.categoryName,
        rightTitle = categoryAnalyticsItem.percentage.toString() + "%",
        rightSubtitle = categoryAnalyticsItem.totalAmount.toString() + " " + currency,
        leftIcon = categoryAnalyticsItem.categoryEmoji,
        rightIcon = R.drawable.ic_drill_in,
        listBackground = MaterialTheme.colorScheme.background,
        leftIconBackground = MaterialTheme.colorScheme.secondary,
        clickable = false,
    )
}