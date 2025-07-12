package com.abrosimov.transactions.edit_transaction.ui.items

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.abrosimov.ui.composableFunctions.CustomListItem

@Composable
fun CommentEditItem(comment: String, onClick: () -> (Unit)) {
    CustomListItem(
        leftTitle = "Комментарий",
        rightTitle = comment,
        listHeight = 70,
        listBackground = MaterialTheme.colorScheme.background,
        clickable = true,
        onClick = onClick
    )
    HorizontalDivider()
}