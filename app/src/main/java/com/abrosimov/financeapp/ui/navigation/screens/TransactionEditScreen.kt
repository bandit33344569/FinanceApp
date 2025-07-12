package com.abrosimov.financeapp.ui.navigation.screens

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class TransactionEditScreen(val id: Int? = null): NavKey {
}