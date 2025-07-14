package com.abrosimov.impl.network_client


import com.abrosimov.impl.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
/**
 * Перехватчик (Interceptor), добавляющий заголовок авторизации с Bearer-токеном к каждому исходящему HTTP-запросу.
 *
 * Использует [BEARER_TOKEN] для получения токена и модифицирует исходящие запросы,
 * добавляя заголовок `Authorization: Bearer <токен>`.
 */
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val token = BuildConfig.BEARER_TOKEN
        val newRequest = request.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }

}