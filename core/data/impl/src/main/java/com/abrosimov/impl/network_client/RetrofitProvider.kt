package com.abrosimov.impl.network_client

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Объект, предоставляющий настроенный экземпляр [retrofit2.Retrofit] для работы с сетевым API.
 *
 * Инициализирует клиент с добавлением:
 * - [AuthInterceptor] — для автоматической авторизации через Bearer-токен,
 * - [okhttp3.logging.HttpLoggingInterceptor] — для логирования HTTP-запросов и ответов (уровень BODY).
 *
 * Базовый URL: `https://shmr-finance.ru/api/v1/ `
 */
object RetrofitProvider {
    private lateinit var _instance: Retrofit
    val instance get() = _instance

    val gson = GsonBuilder()
        .serializeNulls()
        .create()

    /**
     * Инициализирует Retrofit с настроенными параметрами.
     *
     * Создаёт:
     * - OkHttpClient с перехватчиками,
     * - Retrofit с поддержкой конвертации JSON через Gson.
     */
    fun init() {
        val okHttp =
            OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor())
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

        _instance =
            Retrofit.Builder()
                .baseUrl("https://shmr-finance.ru/api/v1/")
                .client(okHttp)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }
}