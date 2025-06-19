package com.abrosimov.financeapp.ui.screens

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import com.abrosimov.financeapp.domain.models.mock.ExpensesMockData
import com.abrosimov.financeapp.ui.misc.ExpenseListItem
import com.abrosimov.financeapp.ui.misc.ExpensesAndIncomeHeader
import com.abrosimov.financeapp.ui.models.Expense


@Composable
fun ExpensesScreen(){
    val expenses: List<Expense> = ExpensesMockData.mockExpenses
    Column {
        ExpensesAndIncomeHeader("436 558 ₽")
        LazyColumn {
            items(expenses){
                expense ->
                ExpenseListItem(
                    expense = expense,
                )
                HorizontalDivider()
            }
        }
    }
}