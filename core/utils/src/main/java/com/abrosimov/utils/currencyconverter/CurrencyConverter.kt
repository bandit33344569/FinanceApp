package com.abrosimov.utils.currencyconverter

fun convertCurrencyToSymbol(currency: String): String{
    return when (currency){
        "RUB" -> "₽"
        "USD" -> "$"
        "EUR" -> "€"
        else -> currency
    }
}