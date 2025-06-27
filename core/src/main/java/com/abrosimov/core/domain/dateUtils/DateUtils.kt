package com.abrosimov.core.domain.dateUtils

import java.text.SimpleDateFormat
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
          return  isoFormat.parse(str)
    }

    // Дата -> человекочитаемый вид
    fun dateToDisplayString(date: Date): String {
        return displayFormat.format(date)
    }

    // Текущая дата в ISO формате
    fun nowIsoString(): String {
        return dateToIsoString(Date())
    }

    // Сегодня как Date
    fun today(): Date {
        return Date()
    }
}