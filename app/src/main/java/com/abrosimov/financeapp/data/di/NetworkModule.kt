package com.abrosimov.financeapp.data.di

import android.app.Application
import com.abrosimov.financeapp.config.Config
import com.abrosimov.financeapp.data.network.AndroidNetworkMonitor
import com.abrosimov.financeapp.data.network.FinanceApi
import com.abrosimov.financeapp.data.network.NetworkMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideAuthInterceptor(): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request()
                val token = Config.BEARER_TOKEN
                val newRequest = request.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
                return chain.proceed(newRequest)
            }
        }
    }

    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    fun provideApiService(client: OkHttpClient): FinanceApi {
        return Retrofit.Builder()
            .baseUrl("https://shmr-finance.ru/api/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FinanceApi::class.java)
    }

    @Provides
    fun provideNetworkMonitor(context: Application): NetworkMonitor {
        return AndroidNetworkMonitor(context)
    }
}