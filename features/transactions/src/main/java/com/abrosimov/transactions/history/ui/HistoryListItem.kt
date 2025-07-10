package com.abrosimov.transactions.history.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.abrosimov.transactions.expenses.domain.models.Expense
import com.abrosimov.transactions.incomes.domain.models.Income
import com.abrosimov.ui.composableFunctions.CustomListItem
import com.abrosimov.utils.dateutils.DateUtils
import com.abrosimov.transactions.R
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