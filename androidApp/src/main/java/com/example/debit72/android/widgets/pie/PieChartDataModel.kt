package com.example.debit72.android.widgets.pie

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.rosa.consultant.personal_office.presentation.widgets.colorsPieBarChart
import com.rosa.consultant.personal_office.presentation.widgets.getColor


class PieChartDataModel(list: List<ValueProfit>) {

    var sliceThickness by mutableStateOf(25F)

    var pieChartData: PieChartData = PieChartData(slices = listOf())

    private val colorsIterator = colorsPieBarChart.listIterator()

    init {
        val data = mutableListOf<PieChartData.Slice>()
        if (list.isNotEmpty())
            list.forEach { valueProfit ->
                data.add(
                    PieChartData.Slice(
                        color = if (valueProfit.value > 0) getColor(colorsIterator) else Color(
                            0xFF78787C
                        ),
                        name = valueProfit.name,
                        sum = valueProfit.value,
                        value = (valueProfit.value / list.sumOf { it.value }).toFloat()
                    )
                )
            }
        pieChartData = PieChartData(slices = data)
    }
}

data class ValueProfit(
    val name: Int,
    val value: Double
)