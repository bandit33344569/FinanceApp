package com.abrosimov.financeapp.ui.navigation.screensconfigs

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.res.painterResource
import com.abrosimov.financeapp.R

fun createTodayExpensesConfig(navigateToHistoryExpense: (() -> Unit)?): ScreenConfig {
    return ScreenConfig(
        title = "Расходы сегодня",
        navigationIcon = { },
        actions = {
            IconButton(onClick = { navigateToHistoryExpense?.invoke() }) {
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