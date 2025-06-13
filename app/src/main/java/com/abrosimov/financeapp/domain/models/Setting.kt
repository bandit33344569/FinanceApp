package com.abrosimov.financeapp.domain.models

import androidx.compose.ui.graphics.vector.ImageVector

data class Setting(
    val title: String,
    val description: String? = null,
    val icon: ImageVector? = null,
    val isSwitchable: Boolean = false,
    val switchState: Boolean = false,
    val onClick: () -> Unit
) {
}
