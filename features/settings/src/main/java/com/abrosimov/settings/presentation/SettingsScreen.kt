package com.abrosimov.settings.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abrosimov.impl.viewmodel.SettingsViewModel
import com.abrosimov.settings.R
import com.abrosimov.ui.composableFunctions.CustomListItem
import androidx.compose.runtime.collectAsState

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    navigateToChangeColorScreen: () -> Unit
) {
    Column {
        ThemeSettingListElement(checked = viewModel.isDarkTheme.collectAsState().value) { viewModel.toggleTheme() }
        ColorSchemeSettingsElement { navigateToChangeColorScreen() }
    }
}


@Composable
fun ColorSchemeSettingsElement(onClick: () -> Unit) {
    CustomListItem(
        leftTitle = "Основной цвет",
        rightIcon = R.drawable.ic_details,
        listHeight = 56,
        listBackground = MaterialTheme.colorScheme.background,
        clickable = true,
        onClick = onClick
    )
    HorizontalDivider()
}


@Composable
fun ThemeSettingListElement(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            "Темная тема",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                checkedTrackColor = MaterialTheme.colorScheme.primaryContainer
            )
        )
    }
    HorizontalDivider()
}



