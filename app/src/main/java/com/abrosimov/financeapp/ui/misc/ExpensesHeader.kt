package com.abrosimov.financeapp.ui.misc

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.abrosimov.financeapp.ui.theme.FinanceAppTheme

@Composable
fun ExpensesHeader(
    amount: String
){
    FinanceAppTheme {
        CustomListItem(
            headlineContent = @Composable { Text("Всего") },
            trailingContent = {
                Row(
                    modifier = Modifier.height(IntrinsicSize.Min),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(amount, style = MaterialTheme.typography.titleMedium)
                }
            },
            containerColor = MaterialTheme.colorScheme.secondary,
            addDivider = true
        )
    }
}