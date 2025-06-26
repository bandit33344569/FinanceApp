package com.abrosimov.financeapp.data.di

import com.abrosimov.financeapp.data.network.FinanceApi
import com.abrosimov.financeapp.data.repo.FinanceRepositoryImpl
import com.abrosimov.financeapp.data.network.NetworkMonitor
import com.abrosimov.financeapp.domain.repo.FinanceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideFinanceRepository(
        api: FinanceApi,
        networkMonitor: NetworkMonitor
    ): FinanceRepository {
        return FinanceRepositoryImpl(api, networkMonitor)
    }

}