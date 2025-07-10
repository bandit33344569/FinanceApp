package com.abrosimov.impl.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class AccountDetailsRepository @Inject constructor(
    private val context: Context
) {
    private val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == "selected_currency") {
                val value = getSelectedCurrency()
                _selectedCurrency.tryEmit(value)
            }
            if (key == "account_id") {
                val value = getAccountId()
                _accountId.tryEmit(value)
            }
        }

    init {
        prefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }


    private val _selectedCurrency = MutableStateFlow(getSelectedCurrency())
    val selectedCurrency: StateFlow<String> = _selectedCurrency.asStateFlow()

    fun getSelectedCurrency(): String {
        return prefs.getString("selected_currency", "RUB") ?: "RUB"
    }

    fun setSelectedCurrency(currency: String) {
        prefs.edit { putString("selected_currency", currency) }
    }


    private val _accountId = MutableStateFlow(getAccountId())
    val accountId: StateFlow<Int> = _accountId

    fun getAccountId(): Int {
        return prefs.getInt("account_id", 10) ?: 10
    }

    fun setAccountId(id: Int) {
        prefs.edit { putInt("account_id", id) }
    }
}