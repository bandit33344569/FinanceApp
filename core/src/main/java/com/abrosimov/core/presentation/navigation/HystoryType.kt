package com.abrosimov.core.presentation.navigation

import androidx.navigation3.runtime.NavKey

/**
 * Sealed класс, представляющий типы истории в навигации приложения.
 *
 * Используется как ключ навигации ([NavKey]) для перехода к экрану истории с различным содержимым:
 * - История расходов (`Expenses`)
 * - История доходов (`Income`)
 *
 * Позволяет использовать единую реализацию [HistoryScreen], но с разным контентом,
 * в зависимости от типа, переданного при навигации.
 */
sealed class HistoryType : NavKey {
    object Expenses : HistoryType()
    object Income : HistoryType()
}