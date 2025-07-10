package com.abrosimov.transactions.incomes.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.abrosimov.transactions.incomes.domain.models.Income
import com.abrosimov.ui.composableFunctions.CustomListItem
import com.abrosimov.transactions.R
@Composable
fun IncomeListItem(
    income: Income,
    onDetailClick: () -> Unit
){
    CustomListItem(
        leftTitle = income.source,
        rightTitle = income.amount + " " + income.currency,
        rightIcon = R.drawable.ic_drill_in,
        listBackground = MaterialTheme.colorScheme.background,
        leftIconBackground = MaterialTheme.colorScheme.secondary,
        clickable = true,
        onClick = onDetailClick
    )
}