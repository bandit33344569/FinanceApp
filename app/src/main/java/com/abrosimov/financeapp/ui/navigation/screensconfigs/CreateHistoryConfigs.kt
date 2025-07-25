package com.abrosimov.financeapp.ui.navigation.screensconfigs

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.res.painterResource
import com.abrosimov.financeapp.R

fun createHistoryIncomesConfig(
    navigateBack: (() -> Unit)?,
    navigateToAnalyticsIncomesScreen: (() -> Unit)?
): ScreenConfig {
    return ScreenConfig(
        title = "Моя история",
        navigationIcon = {
            IconButton(onClick = { navigateBack?.invoke() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "назад"
                )
            }
        },
        actions = {
            IconButton(onClick = { navigateToAnalyticsIncomesScreen?.invoke() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_analize_history),
                    contentDescription = "анализ истории"
                )
            }
        }
    )
}

fun createHistoryExpensesConfig(
    navigateBack: (() -> Unit)?,
    navigateToAnalyticsExpensesScreen: (() -> Unit)?
): ScreenConfig {
    return ScreenConfig(
        title = "Моя история",
        navigationIcon = {
            IconButton(onClick = { navigateBack?.invoke() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "назад"
                )
            }
        },
        actions = {
            IconButton(onClick = { navigateToAnalyticsExpensesScreen?.invoke() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_analize_history),
                    contentDescription = "анализ истории"
                )
            }
        }
    )
}