package com.abrosimov.core.di

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModelProvider

/**
 * CompositionLocal, предоставляющий [ViewModelProvider.Factory] на уровне Compose-дерева.
 *
 * Используется для доступа к фабрике ViewModel из любого Composable-компонента,
 * без необходимости передавать её вручную через параметры.
 *
 * Требует, чтобы значение было предоставлено через [CompositionLocalProvider]
 * на более высоком уровне в дереве.
 *
 * Если значение не предоставлено, выбрасывает ошибку с сообщением `"No ViewModel Factory provided"`.
 */
val LocalViewModelFactory =
    staticCompositionLocalOf<ViewModelProvider.Factory> {
        error("No ViewModel Factory provided")
    }