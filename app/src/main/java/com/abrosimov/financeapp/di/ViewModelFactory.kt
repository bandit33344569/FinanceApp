package com.abrosimov.financeapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * Фабрика для создания экземпляров [ViewModel] с поддержкой внедрения зависимостей через Dagger.
 *
 * Использует маппинг между классом ViewModel и провайдером, чтобы создавать нужные экземпляры
 * на основе запроса. Поддерживает инъекцию зависимостей в различные типы ViewModel.
 *
 * @property creators Карта, где ключ — это класс ViewModel, а значение — провайдер,
 *                    способный создать соответствующий экземпляр.
 */
class ViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    /**
     * Создает экземпляр ViewModel указанного типа.
     *
     * @param modelClass Класс ViewModel, который необходимо создать.
     * @return Новый экземпляр ViewModel.
     * @throws IllegalArgumentException Если для указанного класса нет подходящего провайдера.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator =
            creators[modelClass] ?: throw IllegalArgumentException("Unknown ViewModel class")
        @Suppress("UNCHECKED_CAST")
        return creator.get() as T
    }
}