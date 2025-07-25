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

    private val dayMonthFormat by lazy {
        SimpleDateFormat("dd:MM", Locale("ru"))
    }

    fun isoStringToDayMonth(isoString: String): String {
        return try {
            val date = isoStringToDate(isoString)
            dateToDayMonth(date)
        } catch (e: Exception) {
            ""
        }
    }

    fun dateToDayMonth(date: Date): String {
        return dayMonthFormat.format(date)
    }

    fun longToIsoString(date: Long): String{
        return dateToIsoString(Date(date))
    }

    fun isoStringToLong(string: String): Long{
        return isoStringToDate(string).time
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

    fun getEndOfMonth(date: Date): Date {
        val calendar = Calendar.getInstance().apply {
            time = date
            set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
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

    // Сегодня как Date
    fun today(): Date {
        return Date()
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


}