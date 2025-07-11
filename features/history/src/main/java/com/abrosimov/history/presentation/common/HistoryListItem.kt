package com.abrosimov.history.presentation.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.abrosimov.core.domain.dateUtils.DateUtils
import com.abrosimov.core.presentation.composableFunctions.CustomListItem
import com.abrosimov.expenses.domain.models.Expense
import com.abrosimov.history.R
import com.abrosimov.incomes.domain.models.Income

@Composable
fun HistoryExpenseListItem(expense: Expense) {
    CustomListItem(
        leftTitle = expense.title,
        leftSubtitle = expense.subtitle,
        rightTitle = expense.amount + " " + expense.currency,
        rightSubtitle = DateUtils.dateToDayMonthTime(DateUtils.isoStringToDate(expense.createdAt)),
        leftIcon = expense.iconTag,
        rightIcon = R.drawable.ic_drill_in,
        listBackground = MaterialTheme.colorScheme.background,
        leftIconBackground = MaterialTheme.colorScheme.secondary,
        clickable = true,
    )
}

@Composable
fun HistoryIncomeListItem(income: Income) {
    CustomListItem(
        leftTitle = income.source,
        rightTitle = income.amount + " " + income.currency,
        rightSubtitle = DateUtils.dateToDayMonthTime(DateUtils.isoStringToDate(income.createdAt)),
        rightIcon = R.drawable.ic_drill_in,
        listBackground = MaterialTheme.colorScheme.background,
        leftIconBackground = MaterialTheme.colorScheme.secondary,
        clickable = true,
    )
}