package com.abrosimov.utils.theme

import androidx.compose.ui.graphics.Color

enum class AppColor(val preset: ColorPreset) {
    GREEN(
        ColorPreset(
            primary = GreenPrimary,
            secondary = GreenSecondary,
            tertiary = Tertiary,
            onSurface = Color(0xFF1C1B1F)
        )
    ),
    BLUE(
        ColorPreset(
            primary = Color(0xFF2196F3),
            secondary = Color(0xFF64B5F6),
            tertiary = Tertiary,
            onSurface = Color(0xFFFFFFFF)
        )
    ),
    YELLOW(
        ColorPreset(
            primary = Color(0xFFFFEB3B),
            secondary = Color(0xFFFFC107),
            tertiary = Tertiary,
            onSurface = Color(0xFF1C1B1F)
        )
    ),
    PURPLE(
        ColorPreset(
            primary = Color(0xFF9C27B0),
            secondary = Color(0xFFBA68C8),
            tertiary = Tertiary,
            onSurface = Color(0xFFFFFFFF)
        )
    )
}