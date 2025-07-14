package com.abrosimov.financeapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.abrosimov.financeapp.ui.navigation.NavigationRoot
import javax.inject.Inject
import androidx.lifecycle.ViewModelProvider
import com.abrosimov.ui.theme.FinanceAppTheme

/**
 * Основная активность приложения, управляющая отображением UI и внедрением зависимостей.
 *
 * Занимается:
 * - Инициализацией Dagger-зависимостей
 * - Настройкой Compose UI
 * - Интеграцией `ViewModelFactory` через `CompositionLocalProvider`
 * - Управлением навигацией через [NavigationRoot]
 */
class MainActivity : ComponentActivity() {
    /**
     * Фабрика ViewModel, внедряемая через Dagger.
     * Используется для создания ViewModel-объектов в Compose.
     */
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    /**
     * Вызывается при создании активности.
     *
     * 1. Вызывает инъекцию зависимостей через Dagger (`appComponent.inject(this)`).
     * 2. Включает полноэкранный режим (edge-to-edge).
     * 3. Настраивает Compose UI с темой и навигацией.
     *
     * @param savedInstanceState Сохраненное состояние активности (если есть).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                FinanceAppTheme {
                    NavigationRoot(
                    )
                }
        }
    }
}



