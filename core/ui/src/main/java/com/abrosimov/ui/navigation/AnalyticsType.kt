package com.abrosimov.ui.navigation

import androidx.navigation3.runtime.NavKey

sealed class AnalyticsType : NavKey {
    object Expenses : AnalyticsType()
    object Income : AnalyticsType()
}