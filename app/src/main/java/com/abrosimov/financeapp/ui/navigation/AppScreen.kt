package com.abrosimov.financeapp.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class AppScreen : NavKey {
    @Serializable
    data object Expenses : AppScreen(), NavKey
    @Serializable
    data object Income : AppScreen(), NavKey
    @Serializable
    data object Account : AppScreen(), NavKey
    @Serializable
    data object Articles : AppScreen(), NavKey
    @Serializable
    data object Settings : AppScreen(), NavKey
}