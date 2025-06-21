package com.abrosimov.financeapp.domain.repo

import kotlinx.coroutines.delay
import java.io.IOException

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
                if (result.exception is IOException || result.message.contains("500", ignoreCase = true)) {
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