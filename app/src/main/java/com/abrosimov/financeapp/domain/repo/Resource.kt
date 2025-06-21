package com.abrosimov.financeapp.domain.repo

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String, val exception: Exception? = null) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

fun <T, R> Resource<T>.map(transform: (T) -> R): Resource<R> = when (this) {
    is Resource.Success -> Resource.Success(transform(data))
    is Resource.Error -> this
    is Resource.Loading -> this
}

suspend fun <T, R> Resource<T>.flatMap(
    transform: suspend (T) -> Resource<R>
): Resource<R> = when (this) {
    is Resource.Success -> transform(data)
    is Resource.Error -> this
    is Resource.Loading -> this
}
