package com.abrosimov.transactions.edit_transaction.ui.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun CommentInputDialog(
    initialComment: String,
    onSave: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var comment = remember { mutableStateOf(initialComment) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Комментарий") },
        text = {
            TextField(
                value = comment.value,
                label = { Text("Комментарий") },
                onValueChange = { comment.value = it },
                maxLines = 4
            )
        },
        confirmButton = {
            Button(onClick = {
                onSave(comment.value)
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