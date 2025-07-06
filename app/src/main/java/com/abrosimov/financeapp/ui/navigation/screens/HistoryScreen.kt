package com.abrosimov.financeapp.ui.navigation.screens

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * Экран "История".
 *
 * Предоставляет доступ к полной истории транзакций пользователя за разные периоды времени.
 * Используется как отдельный экран навигации.
 */
@Serializable
data object History : NavKey