package com.abrosimov.transactions.expenses.domain.models

/**
 * Класс, представляющий сводную информацию о расходах за определённый период.
 *
 * Содержит:
 * - Список транзакций типа [Expense]
 * - Общую сумму расходов
 * - Валюту (по умолчанию — рубль "₽")
 *
 * Используется в экране "Расходы" для отображения статистики и списка трат.
 *
 * @property expenses Список всех расходов за период.
 * @property totalAmount Общая сумма всех расходов.
 */
data class ExpensesSummary(
    val expenses: List<Expense>,
    val totalAmount: Double,
)
