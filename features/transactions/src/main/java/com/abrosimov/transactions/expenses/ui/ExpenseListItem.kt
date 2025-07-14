package com.abrosimov.transactions.expenses.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.abrosimov.transactions.expenses.domain.models.Expense
import com.abrosimov.ui.composableFunctions.CustomListItem
import com.abrosimov.transactions.R

@Composable
fun ExpenseListItem(
    expense: Expense,
    onClick: () -> (Unit) = {}
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
        onClick = onClick
    )
}