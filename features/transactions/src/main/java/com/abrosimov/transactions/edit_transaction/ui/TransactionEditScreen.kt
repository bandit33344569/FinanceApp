package com.abrosimov.transactions.edit_transaction.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TransactionEditScreen(
    id: Int?,
){
    Text(text = id.toString())
}