package com.abrosimov.financeapp.ui.misc

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.abrosimov.financeapp.R
import com.abrosimov.financeapp.domain.models.Setting

@Composable
fun SettingLitItem(setting: Setting){
    CustomListItem(
        leftTitle = setting.title,
        rightIcon = R.drawable.ic_details,
        listBackground = MaterialTheme.colorScheme.background,
        clickable = true,
        onClick = setting.onClick,
        listHeight = 56
    )
}