package com.abrosimov.utils.models

/**
 * Sealed класс, представляющий возможные состояния результата операции (например, сетевого запроса).
 *
 * Может быть:
 * - [Success] — успешное выполнение с данными.
 * - [Error] — ошибка во время выполнения.
 * - [Loading] — процесс еще выполняется.
 *
 * Используется для обработки состояний UI (например, отображение прогрессбара, ошибок или данных).
 */
sealed class Resource<out T> {
    /**
     * Состояние "успех".
     *
     * @property data Данные, полученные в результате успешной операции.
     */
    data class Success<out T>(val data: T) : Resource<T>()

    /**
     * Состояние "ошибка".
     *
     * @property message Сообщение об ошибке.
     * @property exception Исключение, если доступно.
     */
    data class Error(val message: String, val exception: Exception? = null) : Resource<Nothing>()

    /**
     * Состояние "загрузка".
     *
     * Используется для индикации того, что операция еще не завершена.
     */
    object Loading : Resource<Nothing>()
}

/**
 * Применяет функцию к данным внутри [Resource.Success], возвращая новый [Resource] с измененным типом.
 *
 * Если текущее состояние — [Resource.Error] или [Resource.Loading], оно возвращается без изменений.
 *
 * @param transform Функция, которая преобразует данные из типа `T` в `R`.
 * @return Новый [Resource<R>] с преобразованными данными или тем же состоянием (Error/Loading).
 */
fun <T, R> Resource<T>.map(transform: (T) -> R): Resource<R> = when (this) {
    is Resource.Success -> Resource.Success(transform(data))
    is Resource.Error -> this
    is Resource.Loading -> this
}

/**
 * Позволяет продолжить выполнение асинхронной операции, если текущее состояние — [Resource.Success].
 *
 * Если текущее состояние — [Resource.Error] или [Resource.Loading], оно возвращается без изменений.
 *
 * @param transform Асинхронная функция, принимающая данные из [Resource.Success] и возвращающая новый [Resource].
 * @return Результат [transform], либо исходное состояние [Error] или [Loading].
 */
suspend fun <T, R> Resource<T>.flatMap(
    transform: suspend (T) -> Resource<R>
): Resource<R> = when (this) {
    is Resource.Success -> transform(data)
    is Resource.Error -> this
    is Resource.Loading -> this
}
