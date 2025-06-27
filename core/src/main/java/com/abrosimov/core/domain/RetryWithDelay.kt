package com.abrosimov.core.domain

import kotlinx.coroutines.delay
import java.io.IOException

/**
 * Повторяет выполнение suspend-функции, возвращающей [Resource], при возникновении ошибки.
 *
 * Полезно для сетевых запросов, где может потребоваться повторная попытка при:
 * - Неустойчивом интернете (IOException)
 * - Временных ошибках сервера (500 Internal Server Error)
 *
 * @param maxAttempts Максимальное количество попыток (по умолчанию 3).
 * @param delayMillis Задержка между попытками в миллисекундах (по умолчанию 2000 мс).
 * @param block Блок кода, возвращающий [Resource]. Обычно это вызов UseCase или API.
 * @return Результат типа [Resource]:
 *         - [Resource.Success] если хотя бы одна попытка завершилась успешно.
 *         - [Resource.Error] если все попытки завершились ошибкой.
 */
suspend fun <T> retryWithDelay(
    maxAttempts: Int = 3,
    delayMillis: Long = 2000L,
    block: suspend () -> Resource<T>
): Resource<T> {
    var attempt = 0
    var lastError: Resource.Error? = null

    while (attempt < maxAttempts) {
        val result = block()

        when (result) {
            is Resource.Success -> return result
            is Resource.Error -> {
                attempt++
                if (result.exception is IOException || result.message.contains(
                        "500",
                        ignoreCase = true
                    )
                ) {
                    if (attempt < maxAttempts) {
                        delay(delayMillis)
                        continue
                    }
                }
                lastError = result
            }

            is Resource.Loading -> delay(500)
        }
    }

    return lastError ?: Resource.Error("Неизвестная ошибка")
}