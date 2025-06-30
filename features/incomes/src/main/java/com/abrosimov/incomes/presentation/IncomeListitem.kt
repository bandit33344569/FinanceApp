package com.abrosimov.incomes.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.abrosimov.core.presentation.composableFunctions.CustomListItem
import com.abrosimov.incomes.R
import com.abrosimov.incomes.domain.models.Income

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