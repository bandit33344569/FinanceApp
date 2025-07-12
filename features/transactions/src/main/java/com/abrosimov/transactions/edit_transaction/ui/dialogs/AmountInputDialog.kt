package com.abrosimov.transactions.edit_transaction.ui.dialogs

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun AmountInputDialog(
    initialAmount: String,
    onSave: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var amount = remember { mutableStateOf(initialAmount) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Введите сумму") },
        text = {
            TextField(
                value = amount.value,
                onValueChange = { amount.value = it },
                label = { Text("Сумма") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        },
        confirmButton = {
            Button(onClick = {
                onSave(amount.value)
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}