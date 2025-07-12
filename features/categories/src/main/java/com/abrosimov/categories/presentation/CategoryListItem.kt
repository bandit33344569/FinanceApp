package com.abrosimov.categories.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.abrosimov.impl.models.Category
import com.abrosimov.ui.composableFunctions.CustomListItem

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