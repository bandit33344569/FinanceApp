package com.abrosimov.financeapp.ui.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.abrosimov.financeapp.ui.misc.ExpensesHeader
import com.abrosimov.financeapp.ui.misc.IncomeListItem
import com.abrosimov.financeapp.ui.models.Income

@Composable
fun IncomeScreen() {
    val incomes: List<Income> = listOf(
        Income(
            id = 1,
            amount = "500 000 ₽",
            source = "Зарплата"
        ),
        Income(
            id = 2,
            amount = "100 000 ₽",
            source = "Подработка"
        )
    )
    Column {
        ExpensesHeader("-600 000 ₽")
        LazyColumn {
            items(incomes) { income ->
                IncomeListItem(income)
            }
        }
    }
}