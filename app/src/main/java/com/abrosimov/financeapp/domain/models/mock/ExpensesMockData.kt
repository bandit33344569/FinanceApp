package com.abrosimov.financeapp.domain.models.mock

import com.abrosimov.financeapp.ui.models.Expense

object ExpensesMockData {
    val mockExpenses = listOf(
        Expense(
            id = "1",
            title = "Аренда квартиры",
            createdAt = "2024-02-25T14:30:00",
            trailTag = "Сумма",
            amount = "100 000 ₽",
            iconTag = "\uD83C\uDFE0"
        ),
        Expense(
            id = "2",
            title = "Одежда",
            createdAt = "2024-02-24T10:15:00",
            trailTag = "Сумма",
            amount = "100 000 ₽",
            iconTag = "\uD83D\uDC57"
        ),
        Expense(
            id = "3",
            title = "На собачку",
            subtitle = "Джек",
            createdAt = "2024-02-23T18:45:00",
            trailTag = "Сумма",
            amount = "100 000 ₽",
            iconTag = "\uD83D\uDC36"
        ),
        Expense(
            id = "4",
            title = "Ремонт квартиры",
            createdAt = "2024-02-22T12:20:00",
            trailTag = "Сумма",
            amount = "100 000 ₽",
            iconTag = "\uD83D\uDEE0\uFE0F"
        ),
        Expense(
            id = "5",
            title = "Продукты",
            createdAt = "2024-02-21T09:30:00",
            trailTag = "Сумма",
            amount = "100 000 ₽",
            iconTag = "\uD83D\uDED2"
        ),
        Expense(
            id = "6",
            title = "Спортзал",
            createdAt = "2024-02-20T16:00:00",
            trailTag = "Сумма",
            amount = "100 000 ₽",
            iconTag = "\uD83C\uDFCB\uD83C\uDFFD"
        ),
        Expense(
            id = "7",
            title = "Медицина",
            createdAt = "2024-02-19T14:45:00",
            trailTag = "Сумма",
            amount = "100 000 ₽",
            iconTag = "\uD83D\uDC8A"
        ),
        Expense(
            id = "8",
            title = "Медицина",
            createdAt = "2024-02-19T14:45:00",
            trailTag = "Сумма",
            amount = "100 000 ₽",
            iconTag = "\uD83D\uDC8A"
        ),
        Expense(
            id = "9",
            title = "Медицина",
            createdAt = "2024-02-19T14:45:00",
            trailTag = "Сумма",
            amount = "100 000 ₽",
            iconTag = "\uD83D\uDC8A"
        )
    )
}