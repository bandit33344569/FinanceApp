package com.abrosimov.financeapp.ui

import android.app.Application
import android.content.Context
import com.abrosimov.financeapp.di.AppComponent
import com.abrosimov.financeapp.di.DaggerAppComponent

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

    /**
     * Вызывается при создании приложения. Здесь происходит инициализация Dagger-компонента.
     */
    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(this)
        appComponent.inject(this)
    }
}

/**
 * Расширение для [Context], позволяющее легко получить доступ к [AppComponent].
 *
 * @return Экземпляр [AppComponent], связанный с этим приложением.
 */
val Context.appComponent: AppComponent
    get() = (applicationContext as FinanceApp).appComponent