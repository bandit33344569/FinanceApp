package com.abrosimov.financeapp.ui.navigation

import androidx.navigation3.runtime.NavKey

sealed class HistoryType : NavKey {
    object Expenses : HistoryType()
    object Income : HistoryType()
}