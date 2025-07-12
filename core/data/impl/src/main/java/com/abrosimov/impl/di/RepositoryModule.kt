package com.abrosimov.impl.di

import com.abrosimov.api.repository.AccountRepository
import com.abrosimov.api.repository.CategoriesRepository
import com.abrosimov.api.repository.TransactionRepository
import com.abrosimov.impl.repository.AccountRepositoryImpl
import com.abrosimov.impl.repository.CategoriesRepositoryImpl
import com.abrosimov.impl.repository.TransactionRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Модуль Dagger, отвечающий за связывание конкретных реализаций репозиториев с их абстрактными интерфейсами.
 *
 * Использует `@Binds` для предоставления зависимостей без необходимости явного создания экземпляров.
 * Все зависимости помечены как `@Singleton`, что гарантирует их однократное создание и использование
 * в пределах графа зависимостей приложения.
 */
@Module
interface RepositoryModule {
    /**
     * Связывает реализацию [com.abrosimov.impl.repository.AccountRepositoryImpl] с её интерфейсом [com.abrosimov.api.repository.AccountRepository].
     *
     * @param impl Конкретная реализация репозитория аккаунтов.
     * @return Экземпляр [com.abrosimov.api.repository.AccountRepository], который будет использоваться в приложении.
     */
    @Binds
    @Singleton
    fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository


    /**
     * Связывает реализацию [com.abrosimov.impl.repository.CategoriesRepositoryImpl] с её интерфейсом [com.abrosimov.api.repository.CategoriesRepository].
     *
     * @param impl Конкретная реализация репозитория категорий.
     * @return Экземпляр [com.abrosimov.api.repository.CategoriesRepository], который будет использоваться в приложении.
     */
    @Binds
    @Singleton
    fun bindCategoryRepository(impl: CategoriesRepositoryImpl): CategoriesRepository

    /**
     * Связывает реализацию [com.abrosimov.impl.repository.TransactionRepositoryImpl] с её интерфейсом [com.abrosimov.api.repository.TransactionRepository].
     *
     * @param impl Конкретная реализация репозитория транзакций.
     * @return Экземпляр [com.abrosimov.api.repository.TransactionRepository], который будет использоваться в приложении.
     */
    @Binds
    @Singleton
    fun bindTransactionRepository(impl: TransactionRepositoryImpl): TransactionRepository

}