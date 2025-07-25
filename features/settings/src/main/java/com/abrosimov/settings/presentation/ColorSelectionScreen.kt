package com.abrosimov.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.abrosimov.impl.viewmodel.SettingsViewModel
import com.abrosimov.utils.theme.AppColor

@Composable
fun ColorSelectionScreen(
    viewModel: SettingsViewModel,
) {
    val selectedColor = viewModel.colorPreset.collectAsState().value
    LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize()
        ) {
            items(AppColor.entries.toTypedArray()) { colorOption ->
                ColorPresetItem(
                    colorOption = colorOption,
                    isSelected = selectedColor == colorOption,
                    onSelect = {
                        viewModel.saveColorPreset(colorOption)
                    }
                )
            }
        }
    }

@Composable
private fun ColorPresetItem(
    colorOption: AppColor,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(12.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .clickable(onClick = onSelect)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorOption.preset.primary,
                            colorOption.preset.tertiary
                        )
                    )
                )
        )

        Text(
            text = getColorName(colorOption),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.3f),
                    shape = MaterialTheme.shapes.small
                )
                .padding(horizontal = 8.dp, vertical = 4.dp),
            color = Color.White,
            style = MaterialTheme.typography.labelMedium
        )

        if (isSelected) {
            CheckmarkIndicator()
        }
    }
}

@Composable
private fun CheckmarkIndicator() {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(24.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun getColorName(color: AppColor): String {
    return when(color) {
        AppColor.GREEN -> "Зеленый"
        AppColor.BLUE -> "голубой"
        AppColor.YELLOW -> "Желтый"
        AppColor.PURPLE -> "Фиолетовый"
    }
}