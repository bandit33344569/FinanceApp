package com.abrosimov.financeapp.di

import android.content.Context
import com.abrosimov.financeapp.ui.FinanceApp
import com.abrosimov.financeapp.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * AppComponent - компонент, содержащий все модули приложения
 * NetworkModule - сетевой модуль
 * RepositoryModule - модуль репоизиториев
 * UseCaseModule - модуль бизнес-логике(useCase)
 * ViewModelModule - модуль для проведения ViewModel через ViewModelFactory
 * AppModule - модуль для проведения ViewModelFactory
 */
@Singleton
@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
        UseCaseModule::class,
        ViewModelModule::class,
        AppModule::class
    ],
)
interface AppComponent {
    fun inject(app: FinanceApp)
    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): AppComponent
    }
}