package com.abrosimov.core.presentation.models

import java.util.Date

/**
 * Класс, представляющий диапазон дат.
 *
 * Используется для фильтрации данных по временному промежутку (например, история транзакций за период).
 *
 * @property start Начало диапазона.
 * @property end Конец диапазона.
 */
data class DateRange(val start: Date, val end: Date)
