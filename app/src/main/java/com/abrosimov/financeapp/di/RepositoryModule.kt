package com.abrosimov.financeapp.di

import com.abrosimov.account.domain.repository.AccountRepository
import com.abrosimov.categories.data.repository.CategoriesRepositoryImpl
import com.abrosimov.categories.domain.repository.CategoriesRepository
import com.abrosimov.data.repository.AccountRepositoryImpl
import com.abrosimov.transactiondata.data.repository.TransactionRepositoryImpl
import com.abrosimov.transactiondata.domain.repository.TransactionRepository
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
     * Связывает реализацию [AccountRepositoryImpl] с её интерфейсом [AccountRepository].
     *
     * @param impl Конкретная реализация репозитория аккаунтов.
     * @return Экземпляр [AccountRepository], который будет использоваться в приложении.
     */
    @Binds
    @Singleton
    fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository


    /**
     * Связывает реализацию [CategoriesRepositoryImpl] с её интерфейсом [CategoriesRepository].
     *
     * @param impl Конкретная реализация репозитория категорий.
     * @return Экземпляр [CategoriesRepository], который будет использоваться в приложении.
     */
    @Binds
    @Singleton
    fun bindCategoryRepository(impl: CategoriesRepositoryImpl): CategoriesRepository

    /**
     * Связывает реализацию [TransactionRepositoryImpl] с её интерфейсом [TransactionRepository].
     *
     * @param impl Конкретная реализация репозитория транзакций.
     * @return Экземпляр [TransactionRepository], который будет использоваться в приложении.
     */
    @Binds
    @Singleton
    fun bindTransactionRepository(impl: TransactionRepositoryImpl): TransactionRepository

}