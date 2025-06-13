package com.abrosimov.financeapp.ui.misc

import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomListItem(
    headlineContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    overlineContent: @Composable() (() -> Unit)? = null,
    supportingContent: @Composable() (() -> Unit)? = null,
    leadingContent: @Composable() (() -> Unit)? = null,
    trailingContent: @Composable() (() -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    val colors: ListItemColors = ListItemColors(
        containerColor = MaterialTheme.colorScheme.background,
        headlineColor = MaterialTheme.colorScheme.onSurface,
        leadingIconColor = MaterialTheme.colorScheme.tertiary,
        overlineColor = MaterialTheme.colorScheme.surface,
        supportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
        trailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledHeadlineColor = MaterialTheme.colorScheme.background,
        disabledLeadingIconColor = MaterialTheme.colorScheme.background,
        disabledTrailingIconColor = MaterialTheme.colorScheme.background
    )

    ListItem(
        headlineContent = headlineContent,
        overlineContent = overlineContent,
        supportingContent = supportingContent,
        leadingContent = leadingContent,
        trailingContent = trailingContent,
        modifier = modifier,
        colors = colors
    )

}