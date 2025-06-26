package com.abrosimov.financeapp.ui.lists

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.abrosimov.financeapp.domain.models.Category

@Composable
fun CategoryListItem(category: Category){
    CustomListItem(
        leftTitle = category.name,
        leftIcon = category.emoji,
        listBackground = MaterialTheme.colorScheme.background,
        leftIconBackground = MaterialTheme.colorScheme.secondary,
        clickable = false,
    )
}