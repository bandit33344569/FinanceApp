package com.abrosimov.financeapp.ui.navigation.screensconfigs

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.res.painterResource
import com.abrosimov.financeapp.R

fun createTodayIncomesConfig(navigateToHistoryIncome: (() -> Unit)?): ScreenConfig {
    return ScreenConfig(
        title = "Доходы сегодня",
        navigationIcon = { },
        actions = {
            IconButton(onClick = { navigateToHistoryIncome?.invoke() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_history),
                    contentDescription = "История"
                )
            }
        },
        fabVisibility = true,
        fabOnClick = {},
    )
}