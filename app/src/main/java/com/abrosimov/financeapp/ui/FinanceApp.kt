package com.abrosimov.financeapp.ui

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import com.abrosimov.account.di.dependencies.AccountDependenciesStore
import com.abrosimov.categories.di.dependencies.CategoriesDependenciesStore
import com.abrosimov.financeapp.di.AppComponent
import com.abrosimov.financeapp.di.DaggerAppComponent
import com.abrosimov.impl.worker.SyncScheduler
import com.abrosimov.impl.worker.SyncWorkerFactory
import com.abrosimov.transactions.di.TransactionDependenciesStore
import javax.inject.Inject

/**
 * Основной класс приложения, отвечающий за инициализацию графа зависимостей.
 *
 * Создает и хранит экземпляр [AppComponent], через который осуществляется внедрение зависимостей
 * во всем приложении. Использует Dagger 2 для управления зависимостями.
 */
class FinanceApp : Application() {
    /**
     * Компонент Dagger, предоставляющий зависимости на уровне приложения.
     */
    lateinit var appComponent: AppComponent
    @Inject
    lateinit var workerFactory: SyncWorkerFactory

    @Inject
    lateinit var syncScheduler: SyncScheduler
    /**
     * Вызывается при создании приложения. Здесь происходит инициализация Dagger-компонента.
     */
    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().context(this).build()
        AccountDependenciesStore.accountDependencies = appComponent

        CategoriesDependenciesStore.categoriesDependencies = appComponent

        TransactionDependenciesStore.transactionsDependencies = appComponent
        appComponent.inject(this)

        WorkManager.initialize(
            this,
            Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()
        )
        syncScheduler.enqueueOneTimeSync()
        syncScheduler.schedulePeriodicSync()
    }

    override fun onTerminate() {
        super.onTerminate()
        syncScheduler.stop()
    }
}

/**
 * Расширение для [Context], позволяющее легко получить доступ к [AppComponent].
 *
 * @return Экземпляр [AppComponent], связанный с этим приложением.
 */
val Context.appComponent: AppComponent
    get() = (applicationContext as FinanceApp).appComponent