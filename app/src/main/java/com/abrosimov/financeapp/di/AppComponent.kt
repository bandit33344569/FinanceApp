package com.abrosimov.financeapp.di

import android.content.Context
import com.abrosimov.account.di.dependencies.AccountDependencies
import com.abrosimov.categories.di.dependencies.CategoriesDependencies
import com.abrosimov.financeapp.ui.FinanceApp
import com.abrosimov.impl.di.CoreModule
import com.abrosimov.impl.di.DatabaseModule
import com.abrosimov.impl.di.NetworkModule
import com.abrosimov.impl.di.RepositoryModule
import com.abrosimov.impl.di.WorkerModule
import com.abrosimov.transactions.di.TransactionsDependencies
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * AppComponent - компонент, содержащий все модули приложения
 * NetworkModule - сетевой модуль
 * RepositoryModule - модуль репоизиториев
 */
@Singleton
@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
        CoreModule::class,
        WorkerModule::class,
        DatabaseModule::class,
    ],
)
interface AppComponent : AccountDependencies, CategoriesDependencies, TransactionsDependencies {
    fun inject(app: FinanceApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(ctx: Context): Builder

        fun build(): AppComponent
    }
}