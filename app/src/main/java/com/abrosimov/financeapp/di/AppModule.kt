package com.abrosimov.financeapp.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abrosimov.core.data.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Модуль Dagger, предоставляющий зависимости на уровне приложения.
 *
 * Содержит методы для предоставления фабрики ViewModel, используемой для создания экземпляров ViewModel
 * с поддержкой внедрения зависимостей через Dagger.
 */
@Module
class AppModule {
    /**
     * Предоставляет фабрику ViewModel, которая используется для создания экземпляров [ViewModel].
     *
     * @param creators Карта, где ключ — это класс ViewModel, а значение — провайдер, который может создать экземпляр этой ViewModel.
     * @return Экземпляр [ViewModelProvider.Factory], используемый для создания ViewModel.
     */
    @Provides
    @Singleton
    fun provideCurrencyRepository(context: Context): CurrencyRepository {
        return CurrencyRepository(context)
    }

    @Provides
    fun provideViewModelFactory(
        creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ): ViewModelProvider.Factory {
        return ViewModelFactory(creators)
    }
}