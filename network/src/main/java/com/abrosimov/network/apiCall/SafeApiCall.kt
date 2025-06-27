package com.abrosimov.network.apiCall

import com.abrosimov.core.domain.Resource
import com.abrosimov.network.networkMonitor.NetworkMonitor
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
 * Выполняет сетевой запрос в безопасной обёртке, обрабатывая возможные ошибки.
 *
 * Позволяет избежать крашей при отсутствии интернета, ошибках сети или неудачных ответах сервера.
 * Возвращает результат в виде [Resource], что удобно для работы с UI и ViewModel.
 *
 * @param networkMonitor Монитор сети, используемый для проверки наличия интернет-соединения.
 * @param block Асинхронный блок кода, выполняющий HTTP-запрос через Retrofit.
 * @return Результат выполнения запроса:
 *         - [Resource.Success] если запрос успешен и тело ответа не null.
 *         - [Resource.Error] если есть ошибка сети, сервер вернул неуспешный статус или ответ пустой.
 *         - [Resource.Error] также возвращается, если нет подключения к интернету.
 */
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