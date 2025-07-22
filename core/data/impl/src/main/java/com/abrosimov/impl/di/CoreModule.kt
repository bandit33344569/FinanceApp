package com.abrosimov.impl.di

import android.content.Context
import com.abrosimov.impl.repository.AccountDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module()
object CoreModule {
    @Provides
    @Singleton
    fun provideAccountDetailsRepository(context: Context): AccountDetailsRepository =
        AccountDetailsRepository(context)

}