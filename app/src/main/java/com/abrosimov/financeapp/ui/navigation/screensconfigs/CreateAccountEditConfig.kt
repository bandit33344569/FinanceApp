package com.abrosimov.financeapp.ui.navigation.screensconfigs

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.res.painterResource
import com.abrosimov.core.presentation.viewmodel.SharedAppViewModel
import com.abrosimov.financeapp.R

fun createAccountEditConfig(
    sharedAppViewModel: SharedAppViewModel,
    onCancelClick: (() -> Unit)?,
    onOkClick: (() -> Unit)?
): ScreenConfig {
    return ScreenConfig(
        title = "Мой счет",
        navigationIcon = {
            IconButton(onClick = {
                sharedAppViewModel.onDiscardClicked()
                onCancelClick?.invoke()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cancel),
                    contentDescription = "отмена"
                )
            }
        },
        actions = {
            IconButton(onClick = {
                sharedAppViewModel.onSaveClicked()
                onOkClick?.invoke()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_ok),
                    contentDescription = "ок"
                )
            }
        },
    )
}