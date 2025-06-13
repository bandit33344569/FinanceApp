package com.abrosimov.financeapp.ui.misc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.abrosimov.financeapp.R
import com.abrosimov.financeapp.ui.models.Expense

@Composable
fun ExpenseListItem(
    expense: Expense,
    modifier: Modifier = Modifier,
    onDetailClick: () -> Unit = {}
) {
    CustomListItem(
        onClick = onDetailClick,
        modifier = modifier.height(70.dp),
        headlineContent = {
            Text(
                text = expense.title,
                style = MaterialTheme.typography.titleMedium
            )
        },
        supportingContent = {
            expense.subtitle?.let {
                Text(
                    text = expense.subtitle.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        leadingContent = {
            Text(
                text = expense.iconTag,
                modifier = Modifier.background(
                    MaterialTheme.colorScheme.secondary,
                    CircleShape
                )
            )
        },
        trailingContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = expense.trailText,
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_drill_in),
                    contentDescription = "Подробности"
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        addDivider = true
    )
}