package com.abrosimov.financeapp.di

import com.abrosimov.account.domain.repository.AccountRepository
import com.abrosimov.account.domain.usecase.GetAccountUseCase
import com.abrosimov.account.domain.usecase.UpdateAccountUseCase
import com.abrosimov.categories.domain.repository.CategoriesRepository
import com.abrosimov.categories.domain.usecase.GetCategoriesUseCase
import com.abrosimov.expenses.domain.GetTodayExpensesUseCase
import com.abrosimov.incomes.domain.usecase.GetTodayIncomesUseCase
import com.abrosimov.transactiondata.domain.repository.TransactionRepository
import com.abrosimov.transactiondata.domain.usecase.GetTransactionsUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Модуль Dagger, предоставляющий зависимости на уровне бизнес-логики (use cases).
 *
 * Содержит методы для предоставления экземпляров use case, используемых в приложении.
 * Все зависимости помечены как `@Singleton`, что гарантирует их однократное создание и использование
 * в пределах графа зависимостей приложения.
 */
@Module
object UseCaseModule {
    /**
     * Предоставляет экземпляр [GetAccountUseCase], используемый для получения данных о счетах.
     *
     * @param repo Реализация репозитория [AccountRepository], предоставляющая данные о счетах.
     * @return Экземпляр [GetAccountUseCase], готовый к использованию.
     */
    @Provides
    @Singleton
    fun provideAccountUseCase(repo: AccountRepository): GetAccountUseCase = GetAccountUseCase(repo)

    @Provides
    @Singleton
    fun provideUpdateAccountUseCase(repo: AccountRepository): UpdateAccountUseCase = UpdateAccountUseCase(
        repo
    )

    /**
     * Предоставляет экземпляр [GetCategoriesUseCase], используемый для получения данных о категориях.
     *
     * @param repo Реализация репозитория [CategoriesRepository], предоставляющая данные о категориях.
     * @return Экземпляр [GetCategoriesUseCase], готовый к использованию.
     */
    @Provides
    @Singleton
    fun provideCategoriesUseCase(repo: CategoriesRepository): GetCategoriesUseCase =
        GetCategoriesUseCase(repo)

    /**
     * Предоставляет экземпляр [GetTodayExpensesUseCase], используемый для получения данных о расходах за сегодня.
     *
     * @param transactionsUseCase Use case [GetTransactionsUseCase], предоставляющий список транзакций.
     * @return Экземпляр [GetTodayExpensesUseCase], готовый к использованию.
     */
    @Provides
    @Singleton
    fun provideGetExpensesTodayUseCase(transactionsUseCase: GetTransactionsUseCase): GetTodayExpensesUseCase =
        GetTodayExpensesUseCase(transactionsUseCase)

    /**
     * Предоставляет экземпляр [GetTodayIncomesUseCase], используемый для получения данных о доходах за сегодня.
     *
     * @param transactionsUseCase Use case [GetTransactionsUseCase], предоставляющий список транзакций.
     * @return Экземпляр [GetTodayIncomesUseCase], готовый к использованию.
     */
    @Provides
    @Singleton
    fun provideGetIncomesTodayUseCase(transactionsUseCase: GetTransactionsUseCase): GetTodayIncomesUseCase =
        GetTodayIncomesUseCase(transactionsUseCase)

    /**
     * Предоставляет экземпляр [GetTransactionsUseCase], используемый для получения списка транзакций.
     *
     * @param repo Реализация репозитория [TransactionRepository], предоставляющая данные о транзакциях.
     * @return Экземпляр [GetTransactionsUseCase], готовый к использованию.
     */
    @Provides
    @Singleton
    fun provideTransactionUseCase(repo: TransactionRepository): GetTransactionsUseCase =
        GetTransactionsUseCase(repo)
}