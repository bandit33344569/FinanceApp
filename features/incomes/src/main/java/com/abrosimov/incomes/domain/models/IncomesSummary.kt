package com.abrosimov.incomes.domain.models

/**
 * Класс, представляющий сводную информацию о доходах за определённый период.
 *
 * Содержит:
 * - Список транзакций типа [Income]
 * - Общую сумму доходов
 * - Валюту (по умолчанию — рубль "₽")
 *
 * Используется в экране "История" для отображения статистики по доходам.
 *
 * @property incomes Список всех доходов за период.
 * @property totalAmount Общая сумма всех доходов.
 * @property currency Валюта, в которой отображаются суммы (по умолчанию "₽").
 */
data class IncomesSummary(
    val incomes: List<Income>,
    val totalAmount: Double,
    val currency: String = "₽"
)