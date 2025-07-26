package com.abrosimov.impl.di

import android.content.Context
import com.abrosimov.impl.DataStore.AppSettingsDataStore
import com.abrosimov.impl.repository.AccountDetailsRepository
import com.abrosimov.impl.repository.AppSettingsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module()
object CoreModule {
    @Provides
    @Singleton
    fun provideAccountDetailsRepository(context: Context): AccountDetailsRepository =
        AccountDetailsRepository(context)

    @Provides
    @Singleton
    fun provideAppSettingsDataStore(
        context: Context
    ): AppSettingsDataStore {
        return AppSettingsDataStore(context)
    }

    @Provides
    @Singleton
    fun provideAppSettingsRepository(
        dataStore: AppSettingsDataStore
    ): AppSettingsRepository {
        return AppSettingsRepository(dataStore)
    }
}