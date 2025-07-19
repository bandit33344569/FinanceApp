package com.abrosimov.impl.di

import android.content.Context
import com.abrosimov.api.service.remote.AccountApi
import com.abrosimov.api.service.remote.CategoriesApi
import com.abrosimov.api.service.remote.TransactionsApi
import com.abrosimov.impl.networkMonitor.AndroidNetworkMonitor
import com.abrosimov.impl.networkMonitor.NetworkMonitor
import com.abrosimov.impl.network_client.RetrofitProvider
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Модуль Dagger, предоставляющий зависимости, связанные с сетевыми компонентами приложения.
 *
 * Содержит методы для предоставления:
 * - Монитора сети ([com.abrosimov.impl.networkMonitor.NetworkMonitor])
 * - Экземпляра [retrofit2.Retrofit] для выполнения HTTP-запросов
 * - API-интерфейсов для взаимодействия с сервером: [AccountApi], [CategoriesApi], [TransactionsApi]
 */
@Module
object NetworkModule {
    /**
     * Предоставляет реализацию [com.abrosimov.impl.networkMonitor.NetworkMonitor] для отслеживания состояния интернет-соединения.
     *
     * @param context Контекст Android-приложения, необходимый для доступа к системным сервисам.
     * @return Экземпляр [com.abrosimov.impl.networkMonitor.NetworkMonitor], используемый для проверки наличия интернет-соединения.
     */
    @Provides
    @Singleton
    fun provideNetworkMonitor(context: Context): NetworkMonitor {
        return AndroidNetworkMonitor(context)
    }

    /**
     * Предоставляет настроенный экземпляр [retrofit2.Retrofit] для работы с REST API.
     *
     * Retrofit инициализируется через [com.abrosimov.impl.network_client.RetrofitProvider], который содержит базовую конфигурацию.
     *
     * @return Настроенный экземпляр [retrofit2.Retrofit].
     */
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        RetrofitProvider.init()
        return RetrofitProvider.instance
    }

    /**
     * Предоставляет API-интерфейс для работы с аккаунтами на сервере.
     *
     * @param retrofit Экземпляр [retrofit2.Retrofit], используемый для создания API-интерфейса.
     * @return Экземпляр [AccountApi], готовый к использованию.
     */
    @Provides
    @Singleton
    fun provideAccountApi(retrofit: Retrofit): AccountApi {
        return retrofit.create(AccountApi::class.java)
    }

    /**
     * Предоставляет API-интерфейс для работы с категориями на сервере.
     *
     * @param retrofit Экземпляр [retrofit2.Retrofit], используемый для создания API-интерфейса.
     * @return Экземпляр [CategoriesApi], готовый к использованию.
     */
    @Provides
    @Singleton
    fun provideCategoryApi(retrofit: Retrofit): CategoriesApi {
        return retrofit.create(CategoriesApi::class.java)
    }

    /**
     * Предоставляет API-интерфейс для работы с транзакциями на сервере.
     *
     * @param retrofit Экземпляр [retrofit2.Retrofit], используемый для создания API-интерфейса.
     * @return Экземпляр [TransactionsApi], готовый к использованию.
     */
    @Provides
    @Singleton
    fun provideTransactionApi(retrofit: Retrofit): TransactionsApi {
        return retrofit.create(TransactionsApi::class.java)
    }
}