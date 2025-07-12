package com.abrosimov.transactions.edit_transaction.ui.items

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.abrosimov.ui.composableFunctions.CustomListItem

@Composable
fun TimeEditItem(time: String, onClick: () -> (Unit)) {
    CustomListItem(
        leftTitle = "Время",
        rightTitle = time,
        listHeight = 70,
        listBackground = MaterialTheme.colorScheme.background,
        clickable = true,
        onClick = onClick
    )
    HorizontalDivider()
}