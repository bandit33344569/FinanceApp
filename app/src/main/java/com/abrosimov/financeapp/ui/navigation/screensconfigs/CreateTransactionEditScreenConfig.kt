package com.abrosimov.financeapp.ui.navigation.screensconfigs

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.res.painterResource
import com.abrosimov.financeapp.R
import com.abrosimov.ui.viewmodel.SharedAppViewModel

fun createTransactionEditScreenConfig(
    sharedAppViewModel: SharedAppViewModel,
    onCancelClick: (() -> Unit)?,
    onOkClick: (() -> Unit)?
): ScreenConfig {
    return ScreenConfig(
        title = "Мои транзакции",
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