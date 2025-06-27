package com.abrosimov.categories.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abrosimov.categories.domain.usecase.GetCategoriesUseCase
import com.abrosimov.core.domain.Resource
import com.abrosimov.core.domain.models.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel для экрана, отображающего список категорий с возможностью поиска.
 *
 * Содержит:
 * - Состояние списка категорий ([categories])
 * - Логику фильтрации по поисковому запросу
 * - Метод загрузки данных через [GetCategoriesUseCase]
 *
 * Использует [Resource] для обработки состояний:
 * - Успех ([Resource.Success])
 * - Ошибка ([Resource.Error])
 * - Загрузка ([Resource.Loading])
 *
 * @property getCategoriesUseCase UseCase, используемый для получения данных о категориях.
 */
class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : ViewModel() {
    /**
     * Текущее состояние списка категорий, представленное через [MutableStateFlow].
     * Наблюдаемое значение используется в UI для отображения прогресса, данных или ошибок.
     */
    private val _categories = MutableStateFlow<Resource<List<Category>>>(Resource.Loading)

    /**
     * Публичный доступ к списку категорий.
     * Не позволяет изменять состояние извне (только чтение).
     */
    val categories: StateFlow<Resource<List<Category>>> = _categories

    /**
     * Текст поискового запроса, введённый пользователем.
     * Обновляется через метод [updateSearchQuery].
     */
    private val _searchQuery = MutableStateFlow("")

    /**
     * Публичный доступ к поисковому запросу.
     * Не позволяет изменять состояние извне (только чтение).
     */
    val searchQuery: StateFlow<String> = _searchQuery

    /**
     * Обновляет поисковой запрос, который используется для фильтрации списка категорий.
     *
     * @param query Поисковая строка, введенная пользователем.
     */
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    /**
     * Фильтрованный список категорий, зависящий от текущего результата [categories] и поискового запроса [searchQuery].
     *
     * Возвращает:
     * - [Resource.Loading], если данные еще загружаются.
     * - [Resource.Error], если произошла ошибка при получении данных.
     * - [Resource.Success], содержащий отфильтрованный список, если есть совпадения.
     * - [Resource.Success] с пустым списком, если совпадений нет.
     */
    val filteredCategoriesWithResource: StateFlow<Resource<List<Category>>> = combine(
        categories,
        searchQuery
    ) { resource, query ->
        when (resource) {
            is Resource.Loading -> Resource.Loading
            is Resource.Error -> Resource.Error(resource.message)
            is Resource.Success -> {
                val filteredList = if (query.isBlank()) {
                    resource.data
                } else {
                    resource.data.filter {
                        it.name.contains(query, ignoreCase = true)
                    }
                }

                if (filteredList.isEmpty() && query.isNotBlank()) {
                    Resource.Success(emptyList())
                } else {
                    Resource.Success(filteredList)
                }
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        Resource.Loading
    )

    /**
     * Метод для асинхронной загрузки информации о категориях.
     *
     * 1. Сначала устанавливает состояние [Resource.Loading].
     * 2. Выполняет запрос через [GetCategoriesUseCase].
     * 3. Обновляет [_categories] результатом операции.
     */
    fun loadCategories() {
        viewModelScope.launch {
            _categories.value = Resource.Loading
            _categories.value = getCategoriesUseCase()
        }
    }
}