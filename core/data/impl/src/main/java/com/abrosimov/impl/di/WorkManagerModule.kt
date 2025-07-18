package com.abrosimov.impl.di

import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import com.abrosimov.impl.service.SyncWorkerFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object WorkManagerModule {

    @Provides
    @Singleton
    fun provideWorkerFactory(syncWorkerFactory: SyncWorkerFactory): Configuration {
        val delegatingFactory = DelegatingWorkerFactory()
        delegatingFactory.addFactory(syncWorkerFactory)
        return Configuration.Builder()
            .setWorkerFactory(delegatingFactory)
            .build()
    }
}