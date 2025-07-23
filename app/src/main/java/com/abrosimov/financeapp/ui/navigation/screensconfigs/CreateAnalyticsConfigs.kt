package com.abrosimov.financeapp.ui.navigation.screensconfigs

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.res.painterResource
import com.abrosimov.financeapp.R

fun createAnalyticsIncomesConfig(
    navigateBack: (() -> Unit)?,
): ScreenConfig {
    return ScreenConfig(
        title = "Аналитика",
        navigationIcon = {
            IconButton(onClick = { navigateBack?.invoke() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "назад"
                )
            }
        },
        actions = {}
    )
}

fun createAnalyticsExpensesConfig(
    navigateBack: (() -> Unit)?,
): ScreenConfig {
    return ScreenConfig(
        title = "Аналитика",
        navigationIcon = {
            IconButton(onClick = { navigateBack?.invoke() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "назад"
                )
            }
        },
        actions = {}
    )
}