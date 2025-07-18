package com.abrosimov.utils.dateutils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateUtils {

    /**
     * Формат для отображения даты в формате "11 июня 21:53"
     */
    private val dayMonthTimeFormat by lazy {
        SimpleDateFormat("d MMMM HH:mm", Locale("ru"))
    }

    /**
     * Возвращает дату в человекочитаемом формате:
     * Например: "11 июня 21:53"
     */
    fun dateToDayMonthTime(date: Date): String {
        return dayMonthTimeFormat.format(date)
    }

    private val serverDateFormat by lazy {
        SimpleDateFormat("yyyy-MM-dd", Locale("ru")).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
    }

    // Дата -> строка (YYYY-MM-DD)
    fun dateToServerFormat(date: Date): String {
        return serverDateFormat.format(date)
    }

    // Формат для передачи на сервер (ISO 8601)
    private val isoFormat by lazy {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale("ru")).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
    }

    // Человеческий формат для отображения даты
    private val displayFormat by lazy {
        SimpleDateFormat("d MMMM yyyy", Locale("ru"))
    }

    // Получить начало дня
    fun getStartOfDay(date: Date): Date {
        val calendar = Calendar.getInstance().apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.time
    }

    // Получить конец дня
    fun getEndOfDay(date: Date): Date {
        val calendar = Calendar.getInstance().apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }
        return calendar.time
    }

    fun getStartOfMonth(date: Date): Date {
        val calendar = Calendar.getInstance().apply {
            time = date
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.time
    }

    // Дата -> строка (ISO)
    fun dateToIsoString(date: Date): String {
        return isoFormat.format(date)
    }

    // Строка (ISO) -> дата
    fun isoStringToDate(str: String): Date {
        val zonedDateTime = java.time.ZonedDateTime.parse(str)
        return Date.from(zonedDateTime.toInstant())
    }


    // Текущая дата в ISO формате
    fun nowIsoString(): String {
        return dateToIsoString(Date())
    }

    // Сегодня как Date
    fun today(): Date {
        return Date()
    }


    // Формат для вывода даты в виде YYYY-MM-DD
    private val dateOutputFormat by lazy {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    }

    // Формат для вывода времени в виде HH:mm
    private val timeOutputFormat by lazy {
        SimpleDateFormat("HH:mm", Locale.getDefault())
    }

    /**
     * Парсит ISO-строку в объект Date.
     */
    fun parseIsoStringToDate(isoString: String): Date? {
        return try {
            isoFormat.parse(isoString)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Преобразует строку ISO даты в формат YYYY-MM-DD
     */
    fun getDateStringFromIso(isoString: String): String {
        return try {
            val dateTime = java.time.ZonedDateTime.parse(isoString)
            DateTimeFormatter.ISO_LOCAL_DATE.format(dateTime)
        } catch (e: Exception) {
            ""
        }
    }

    fun getTimeStringFromIso(isoString: String): String {
        return try {
            val dateTime = java.time.ZonedDateTime.parse(isoString)
            DateTimeFormatter.ISO_LOCAL_TIME.format(dateTime).substring(0, 5)
        } catch (e: Exception) {
            ""
        }
    }

    fun combineDateAndTimeToIso(dateStr: String, timeStr: String): String {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

        val date = LocalDate.parse(dateStr, dateFormatter)
        val time = LocalTime.parse(timeStr, timeFormatter)

        val dateTime = LocalDateTime.of(date, time)

        val utcFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .withZone(ZoneOffset.UTC)

        return utcFormatter.format(dateTime)
    }

    fun parseTimeString(timeStr: String): Calendar {
        val calendar = Calendar.getInstance().apply {
            clear()
        }

        val parts = timeStr.split(":")
        if (parts.size == 2) {
            calendar.set(Calendar.HOUR_OF_DAY, parts[0].toInt())
            calendar.set(Calendar.MINUTE, parts[1].toInt())
        }

        return calendar
    }

    fun String.padTime(): String {
        val parts = this.split(":")
        if (parts.size != 2) return this
        return "${parts[0].padStart(2, '0')}:${parts[1].padStart(2, '0')}"
    }

    /**
     * Преобразует Date в timestamp (миллисекунды с 1970)
     */
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }

    fun isServerDateNewer(serverDate: String, localDate: String): Boolean {
        val serverTime = isoStringToTimestamp(serverDate)
        val localTime = isoStringToTimestamp(localDate)
        return serverTime > localTime
    }

    // Преобразует ISO-строку в timestamp
    fun isoStringToTimestamp(isoString: String): Long {
        return try {
            val zonedDateTime = java.time.ZonedDateTime.parse(isoString)
            zonedDateTime.toInstant().toEpochMilli()
        } catch (e: Exception) {
            0L
        }
    }

    // Получает текущую дату в ISO формате
    fun getCurrentIsoDate(): String {
        return nowIsoString()
    }
}