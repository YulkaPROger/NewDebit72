package com.example.debit72.android.widgets.pie

import androidx.compose.ui.graphics.Color
import kotlin.math.absoluteValue

data class PieChartData(val slices: List<Slice>) {
    internal val totalLength: Float
        get() {
            return slices.map { it.value.absoluteValue }.sum()
        }

    data class Slice(val name: Int, val sum: Double, val value: Float, val color: Color)
}