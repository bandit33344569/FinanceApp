package com.abrosimov.financeapp.ui.lists

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.abrosimov.financeapp.R
import com.abrosimov.financeapp.ui.models.Expense

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