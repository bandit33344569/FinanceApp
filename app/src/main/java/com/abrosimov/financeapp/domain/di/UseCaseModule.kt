package com.abrosimov.financeapp.domain.di

import com.abrosimov.financeapp.domain.repo.FinanceRepository
import com.abrosimov.financeapp.domain.usecase.GetAccountUseCase
import com.abrosimov.financeapp.domain.usecase.GetCategoriesUseCase
import com.abrosimov.financeapp.domain.usecase.GetTransactionsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetAccountUseCase(repository: FinanceRepository): GetAccountUseCase {
        return GetAccountUseCase(repository)
    }

    @Provides
    fun provideGetCategoriesUseCase(repository: FinanceRepository): GetCategoriesUseCase {
        return GetCategoriesUseCase(repository)
    }

    @Provides
    fun provideGetTransactionsUseCase(repository: FinanceRepository): GetTransactionsUseCase {
        return GetTransactionsUseCase(repository)
    }
}