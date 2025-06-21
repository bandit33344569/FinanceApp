package com.abrosimov.financeapp.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class MainAppScreen : NavKey {
    @Serializable
    data object Expenses : MainAppScreen(), NavKey

    @Serializable
    data object Income : MainAppScreen(), NavKey

    @Serializable
    data object Account : MainAppScreen(), NavKey

    @Serializable
    data object Articles : MainAppScreen(), NavKey

    @Serializable
    data object Settings : MainAppScreen(), NavKey
}

@Serializable
data object History : NavKey