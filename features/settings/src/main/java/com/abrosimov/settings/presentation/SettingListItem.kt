package com.abrosimov.settings.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.abrosimov.settings.R
import com.abrosimov.settings.domain.models.Setting
import com.abrosimov.ui.composableFunctions.CustomListItem

@Composable
fun SettingListItem(setting: Setting){
    CustomListItem(
        leftTitle = setting.title,
        rightIcon = R.drawable.ic_details,
        listBackground = MaterialTheme.colorScheme.background,
        clickable = true,
        onClick = setting.onClick,
        listHeight = 56
    )
}