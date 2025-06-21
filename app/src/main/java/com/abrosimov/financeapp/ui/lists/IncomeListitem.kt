package com.abrosimov.financeapp.ui.lists

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.abrosimov.financeapp.R
import com.abrosimov.financeapp.ui.models.Income

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