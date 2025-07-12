package com.abrosimov.transactions.edit_transaction.ui.items

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.abrosimov.impl.models.Category
import com.abrosimov.transactions.R
import com.abrosimov.ui.composableFunctions.CustomListItem

@Composable
fun CategoryEditItem(category: Category?, onClick: () -> (Unit)) {
    CustomListItem(
        leftTitle = "Статья",
        rightTitle = category?.name ?: "",
        rightIcon = R.drawable.ic_drill_in,
        listHeight = 70,
        listBackground = MaterialTheme.colorScheme.background,
        clickable = true,
        onClick = onClick
    )
    HorizontalDivider()
}