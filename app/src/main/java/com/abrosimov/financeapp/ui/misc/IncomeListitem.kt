package com.abrosimov.financeapp.ui.misc

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.abrosimov.financeapp.R
import com.abrosimov.financeapp.ui.models.Income

@Composable
fun IncomeListItem(
    income: Income
){
    CustomListItem(
        headlineContent = @Composable { Text(income.source) },
        trailingContent = {
            Row(
                modifier = Modifier.height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(income.amount, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_drill_in),
                    contentDescription = "Подробности"
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        addDivider = true
    )
}