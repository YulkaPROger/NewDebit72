package com.rosa.consultant.personal_office.presentation.widgets

import androidx.compose.ui.graphics.Color

val colorsPieBarChart = mutableListOf(
    Color(0XFFFF9A00),
    Color(0XFFF95959),
    Color(0XFF853E3E),
    Color(0XFFFFBF33),
    Color(0XFFCEA489),
    Color(0XFFB61AAE),
    Color(0XFF0F4392),
    Color(0XFFA0DB5E),
    Color(0XFF307672),
    Color(0XFFEEC60A),
    Color(0xFF9FA8DA)
)

fun getColor(colorsIterator: MutableListIterator<Color>): Color {
    return if (colorsIterator.hasNext()) colorsIterator.next()
    else {
        while (colorsIterator.hasPrevious()) {
            colorsIterator.previous()
        }
        colorsIterator.next()
    }
}