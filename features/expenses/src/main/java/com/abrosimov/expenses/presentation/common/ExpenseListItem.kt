package com.abrosimov.expenses.presentation.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.abrosimov.core.presentation.composableFunctions.CustomListItem
import com.abrosimov.expenses.domain.models.Expense
import com.abrosimov.expenses.R

@Composable
fun ExpenseListItem(
    expense: Expense,
    onDetailClick: () -> Unit = {}
) {
    CustomListItem(
        leftTitle = expense.title,
        leftSubtitle = expense.subtitle,
        rightTitle = expense.amount + " " + expense.currency,
        leftIcon = expense.iconTag,
        rightIcon = R.drawable.ic_drill_in,
        listBackground = MaterialTheme.colorScheme.background,
        leftIconBackground = MaterialTheme.colorScheme.secondary,
        clickable = true,
        onClick = onDetailClick
    )
}