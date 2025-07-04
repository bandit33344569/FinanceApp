package com.abrosimov.core.data.repository

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.core.content.edit
import javax.inject.Inject

class CurrencyRepository@Inject constructor(
    private val context: Context) {
    private val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    private val preferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key == "selected_currency") {
            val value = getSelectedCurrency()
            _selectedCurrency.tryEmit(value)
        }
    }

    private val _selectedCurrency = MutableStateFlow(getSelectedCurrency())
    val selectedCurrency: StateFlow<String> = _selectedCurrency.asStateFlow()

    init {
        prefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    fun getSelectedCurrency(): String {
        return prefs.getString("selected_currency", "RUB") ?: "RUB"
    }

    fun setSelectedCurrency(currency: String) {
        prefs.edit { putString("selected_currency", currency) }
    }
}