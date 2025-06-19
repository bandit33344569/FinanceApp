package com.abrosimov.financeapp.ui.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abrosimov.financeapp.ui.misc.CustomListItem


@Composable
fun AccountScreen() {
    AccountBriefUI()
}

@Composable
fun AccountBriefUI() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Balance()
        Currency()
    }
}

@Composable
fun Balance() {
    CustomListItem(
        leftTitle = "Баланс",
        rightTitle = "-670 000 ₽",
        leftIcon = "\uD83D\uDCB0",
        listBackground = MaterialTheme.colorScheme.secondary,
        leftIconBackground = MaterialTheme.colorScheme.background,
        clickable = false,
        listHeight = 56
    )
    HorizontalDivider()
}

@Composable
fun Currency() {
    CustomListItem(
        leftTitle = "Валюта",
        rightTitle = "₽",
        listBackground = MaterialTheme.colorScheme.secondary,
        leftIconBackground = MaterialTheme.colorScheme.background,
        clickable = false,
        listHeight = 56
    )
}
