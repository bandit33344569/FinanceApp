package com.abrosimov.financeapp.ui

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import com.abrosimov.account.di.dependencies.AccountDependenciesStore
import com.abrosimov.categories.di.dependencies.CategoriesDependenciesStore
import com.abrosimov.financeapp.di.AppComponent
import com.abrosimov.financeapp.di.DaggerAppComponent
import com.abrosimov.impl.service.SyncManager
import com.abrosimov.transactions.di.TransactionDependenciesStore
import javax.inject.Inject

/**
 * Основной класс приложения, отвечающий за инициализацию графа зависимостей.
 *
 * Создает и хранит экземпляр [AppComponent], через который осуществляется внедрение зависимостей
 * во всем приложении. Использует Dagger 2 для управления зависимостями.
 */
class FinanceApp : Application() {
    lateinit var appComponent: AppComponent
    @Inject
    lateinit var workManagerConfiguration: Configuration
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

        WorkManager.initialize(this, workManagerConfiguration)

        SyncManager.triggerInitialSync(this)
        SyncManager.schedulePeriodicSync(this)
    }
}

/**
 * Расширение для [Context], позволяющее легко получить доступ к [AppComponent].
 *
 * @return Экземпляр [AppComponent], связанный с этим приложением.
 */
val Context.appComponent: AppComponent
    get() = (applicationContext as FinanceApp).appComponent