package com.abrosimov.financeapp.ui.lists

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun HistoryHeader(
    startDate: String,
    endDate: String, summary: String,
    onStartDateClick: () -> Unit,
    onEndDateClick: () -> Unit
) {
    CustomListItem(
        leftTitle = "Начало",
        rightTitle = startDate,
        listBackground = MaterialTheme.colorScheme.secondary,
        clickable = true,
        listHeight = 56,
        onClick = onStartDateClick
    )
    HorizontalDivider()
    CustomListItem(
        leftTitle = "Конец",
        rightTitle = endDate,
        listBackground = MaterialTheme.colorScheme.secondary,
        clickable = true,
        listHeight = 56,
        onClick = onEndDateClick
    )
    HorizontalDivider()
    CustomListItem(
        leftTitle = "Сумма",
        rightTitle = summary,
        listBackground = MaterialTheme.colorScheme.secondary,
        clickable = false,
        listHeight = 56
    )
    HorizontalDivider()
}