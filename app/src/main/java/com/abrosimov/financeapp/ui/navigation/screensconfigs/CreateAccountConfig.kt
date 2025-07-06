package com.abrosimov.financeapp.ui.navigation.screensconfigs

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.res.painterResource
import com.abrosimov.financeapp.R

fun createAccountConfig(onNavigateToAccountEdit: (() -> Unit)?): ScreenConfig {
    return ScreenConfig(
        title = "Мой счет",
        navigationIcon = { },
        actions = {
            IconButton(onClick = {
                onNavigateToAccountEdit?.invoke()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "Редактировать"
                )
            }
        },
        fabVisibility = false,
        fabOnClick = {},
    )
}