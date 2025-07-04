package com.abrosimov.account.presentation.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun AccountNameDialog(
    initialName: String,
    onSave: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var text = remember { mutableStateOf(initialName) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Изменить название") },
        text = {
            TextField(
                value = text.value,
                onValueChange = { newText ->
                    text.value = newText
                },
                label = { Text("Название счёта") }
            )
        },
        confirmButton = {
            Button(onClick = { onSave(text.value) }) {
                Text("Сохранить")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}