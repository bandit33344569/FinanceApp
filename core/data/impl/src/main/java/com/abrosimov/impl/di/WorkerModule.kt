package com.abrosimov.impl.di

import android.content.Context
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import androidx.work.WorkerFactory
import com.abrosimov.impl.networkMonitor.NetworkMonitor
import com.abrosimov.impl.worker.SyncScheduler
import com.abrosimov.impl.worker.SyncWorkerFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import javax.inject.Singleton

@Module
class WorkerModule {

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