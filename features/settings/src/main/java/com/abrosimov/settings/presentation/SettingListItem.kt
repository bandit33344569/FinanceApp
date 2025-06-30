package com.abrosimov.settings.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.abrosimov.core.presentation.composableFunctions.CustomListItem
import com.abrosimov.settings.R
import com.abrosimov.settings.domain.models.Setting

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