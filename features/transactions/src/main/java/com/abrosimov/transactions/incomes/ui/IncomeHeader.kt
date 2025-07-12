package com.abrosimov.transactions.incomes.ui

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.abrosimov.ui.composableFunctions.CustomListItem

@Composable
fun IncomeHeader(
    amount: String
) {
    CustomListItem(
        leftTitle = "Всего",
        rightTitle = amount,
        listBackground = MaterialTheme.colorScheme.secondary,
        clickable = false,
        listHeight = 56
    )
    HorizontalDivider()
}