package com.abrosimov.financeapp.domain.models


data class Setting(
    val title: String,
    val description: String? = null,
    val onClick: () -> Unit
) {
}
