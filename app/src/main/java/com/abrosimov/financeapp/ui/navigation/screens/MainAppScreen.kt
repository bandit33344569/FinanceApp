package com.abrosimov.financeapp.ui.navigation.screens

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * Базовый sealed класс, представляющий основные экраны приложения, доступные через нижнюю навигацию.
 *
 * Используется для реализации
 * навигации между ключевыми разделами приложения. Каждый экран представлен как объект (data object),
 * чтобы обеспечить уникальность и удобство сравнения.
 *
 * Реализует интерфейс [androidx.navigation3.runtime.NavKey], что позволяет использовать эти экраны в системе навигации Compose.
 */
@Serializable
sealed class MainAppScreen : NavKey {

    @Serializable
    data object Expenses : MainAppScreen(), NavKey

    @Serializable
    data object Income : MainAppScreen(), NavKey

    @Serializable
    data object Account : MainAppScreen(), NavKey

    @Serializable
    data object Articles : MainAppScreen(), NavKey

    @Serializable
    data object Settings : MainAppScreen(), NavKey
}