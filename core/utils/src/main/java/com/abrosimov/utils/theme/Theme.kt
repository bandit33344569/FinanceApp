package com.abrosimov.utils.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DefaultDarkColorScheme = darkColorScheme(
    primary = GreenPrimary,
    secondary = GreenSecondary,
    tertiary = Tertiary,
    onSurface = Color(0xFFFFFFFF),
)

private val DefaultLightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    secondary = GreenSecondary,
    tertiary = Tertiary,
    error = red,
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    surfaceContainer = Color(0xffF3EDF7)

)

@Composable
fun FinanceAppTheme(
    themeMode: ThemeMode = ThemeMode.LIGHT,
    colorPreset: AppColor = AppColor.GREEN,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val isDarkTheme = when (themeMode) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
    }

    val baseColorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (isDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        isDarkTheme -> DefaultDarkColorScheme
        else -> DefaultLightColorScheme
    }

    val colorScheme = baseColorScheme.copy(
        primary = colorPreset.preset.primary,
        secondary = colorPreset.preset.secondary,
        tertiary = colorPreset.preset.tertiary
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

