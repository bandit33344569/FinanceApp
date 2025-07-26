package com.abrosimov.gpaphics.analytics_diagram.color_generator

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

object ColorGenerator {
    private val usedColors = mutableSetOf<Color>()
    private val baseColors = listOf(
        Color(0xFF1F78B4), // Темно-синий
        Color(0xFFE31A1C), // Ярко-красный
        Color(0xFF33A02C), // Зеленый
        Color(0xFFFF7F00), // Оранжевый
        Color(0xFF6A3D9A), // Фиолетовый
        Color(0xFFA6CEE3), // Светло-голубой
        Color(0xFFFB9A99), // Светло-розовый
        Color(0xFFB2DF8A), // Светло-зеленый
        Color(0xFFFDBF6F), // Персиковый
        Color(0xFFCAB2D6), // Светло-фиолетовый
        Color(0xFFB15928), // Коричневый
        Color(0xFF14866D), // Бирюзовый
        Color(0xFF377EB8), // Синий
        Color(0xFF4DAF4A), // Ярко-зеленый
        Color(0xFF984EA3), // Сиреневый
        Color(0xFFE6AB02), // Золотистый
        Color(0xFFF781BF), // Розовый
        Color(0xFFA65628), // Темно-коричневый
        Color(0xFF999999), // Серый
        Color(0xFF7FC97F)  // Салатовый
    )

    fun getRandomColor(): Color {
        if (usedColors.size >= baseColors.size) {
            return Color(
                red = Random.nextInt(256),
                green = Random.nextInt(256),
                blue = Random.nextInt(256)
            )
        }
        var color: Color
        do {
            color = baseColors[Random.nextInt(baseColors.size)]
        } while (!usedColors.add(color))
        return color
    }

    fun resetColors() {
        usedColors.clear()
    }
}