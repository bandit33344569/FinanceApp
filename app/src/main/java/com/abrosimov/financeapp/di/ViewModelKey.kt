package com.abrosimov.financeapp.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Аннотация-ключ для использования в Dagger-модулях при регистрации ViewModel через `@Binds` и `@IntoMap`.
 *
 * Используется вместе с `@Binds` и `@IntoMap` в Dagger-модуле, чтобы связать конкретный класс `ViewModel`
 * с его реализацией. Позволяет автоматически создавать нужные экземпляры ViewModel через `ViewModelFactory`.
 *
 * Пример использования:
 * ```kotlin
 * @Binds
 * @IntoMap
 * @ViewModelKey(MyViewModel::class)
 * abstract fun bindMyViewModel(viewModel: MyViewModel): ViewModel
 * ```
 *
 * @property value Класс ViewModel, который будет использоваться как ключ в Dagger.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)