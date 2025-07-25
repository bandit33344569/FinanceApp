package com.abrosimov.financeapp.ui.navigation.screensconfigs

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.res.painterResource
import com.abrosimov.financeapp.R

fun createColorSelectionScreenConfig(navigateBack: (() -> Unit)?): ScreenConfig {
    return ScreenConfig(
        title = "Выбор цвета",
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