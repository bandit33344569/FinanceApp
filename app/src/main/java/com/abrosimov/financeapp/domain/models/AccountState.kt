package com.abrosimov.financeapp.domain.models

import android.icu.util.Currency

data class AccountState(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
) {
}