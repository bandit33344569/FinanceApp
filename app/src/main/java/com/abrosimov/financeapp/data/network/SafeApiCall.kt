package com.abrosimov.financeapp.data.network

import com.abrosimov.financeapp.domain.repo.Resource
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun <T> safeApiCall(
    networkMonitor: NetworkMonitor,
    block: suspend () -> Response<T>
): Resource<T> {
    if (!networkMonitor.isOnline()) {
        return Resource.Error("Нет подключения к интернету")
    }

    return try {
        val response = block()
        if (response.isSuccessful) {
            val body = response.body() ?: return Resource.Error("Пустой ответ от сервера")
            Resource.Success(body)
        } else {
            Resource.Error("Ошибка сервера: ${response.code()}")
        }
    } catch (e: IOException) {
        Resource.Error("Ошибка сети", e)
    } catch (e: HttpException) {
        Resource.Error("HTTP ошибка: ${e.code()}", e)
    } catch (e: Exception) {
        Resource.Error("Неизвестная ошибка", e)
    }
}