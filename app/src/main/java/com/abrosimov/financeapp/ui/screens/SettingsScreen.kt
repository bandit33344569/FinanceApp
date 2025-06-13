package com.abrosimov.financeapp.ui.screens

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.abrosimov.financeapp.domain.models.Setting
import com.abrosimov.financeapp.ui.misc.CustomListItem
import com.abrosimov.financeapp.R
import com.abrosimov.financeapp.domain.models.mock.SettingsMockData

@Composable
fun SettingsScreen() {
    val settings: List<Setting> = SettingsMockData.getSettings()
    LazyColumn {
        items(settings){ setting ->
            CustomListItem(
                modifier = Modifier.height(56.dp),
                headlineContent = { Text(setting.title) },
                supportingContent = setting.description?.let {
                    @Composable { Text(it) }
                },
                trailingContent = {
                    if (setting.isSwitchable) {
                        Switch(
                            checked = setting.switchState,
                            onCheckedChange = { setting.onClick() }
                        )
                    } else {
                        Icon(painterResource(id = R.drawable.ic_details), contentDescription = "Детали")
                    }
                },
                onClick = setting.onClick,
            )
        }
    }
}



