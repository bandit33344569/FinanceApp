package com.abrosimov.financeapp.ui.lists

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun ExpensesAndIncomeHeader(
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