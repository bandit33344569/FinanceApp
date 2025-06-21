package com.abrosimov.financeapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abrosimov.financeapp.domain.models.Setting
import com.abrosimov.financeapp.domain.models.mock.SettingsMockData
import com.abrosimov.financeapp.ui.lists.SettingLitItem

@Composable
fun SettingsScreen() {
    val settings: List<Setting> = SettingsMockData.getSettings()
    LazyColumn {
        item {
            ThemeSettingListElement()
        }
        items(settings){ setting ->
            SettingLitItem(setting)
            HorizontalDivider()
        }
    }
}

@Composable
fun ThemeSettingListElement(){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(16.dp)
    ) {
        Text(
            "Темная тема",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        Switch(
            checked = false,
            onCheckedChange = {}
        )
    }
    HorizontalDivider()
}



