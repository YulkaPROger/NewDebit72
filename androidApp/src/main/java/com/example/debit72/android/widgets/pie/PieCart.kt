package com.example.debit72.android.widgets.pie

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import kotlin.math.absoluteValue

@Composable
fun PieChart(
    pieChartData: PieChartData,
    modifier: Modifier = Modifier,
    animation: AnimationSpec<Float> = TweenSpec(durationMillis = 700),
    sliceDrawer: ISliceDrawer = SimpleSliceDrawer()
) {
    val transitionProgress = remember(pieChartData.slices) { Animatable(initialValue = 0F) }

    LaunchedEffect(pieChartData.slices) {
        transitionProgress.animateTo(1F, animationSpec = animation)
    }

    DrawChart(
        pieChartData = pieChartData,
        modifier = modifier.fillMaxSize(),
        progress = transitionProgress.value,
        sliceDrawer = sliceDrawer
    )
}

@Composable
private fun DrawChart(
    pieChartData: PieChartData,
    modifier: Modifier,
    progress: Float,
    sliceDrawer: ISliceDrawer
) {
    val slices = pieChartData.slices
    Canvas(modifier = modifier) {
        drawIntoCanvas {
            var startArc = 0F
            val splitterArc = 1F
            val totalLength = pieChartData.totalLength * 360F / (360F - slices.size * splitterArc)
            slices.forEach { slice ->
                val arc = calculateAngle(
                    sliceLength = slice.value.absoluteValue,
                    totalLength = totalLength,
                    progress = progress
                )
                sliceDrawer.drawSlice(
                    drawScope = this,
                    canvas = drawContext.canvas,
                    area = size,
                    startAngle = startArc,
                    sweepAngle = arc,
                    slice = slice
                )
                startArc += arc + splitterArc
            }
        }
    }
}

fun calculateAngle(sliceLength: Float, totalLength: Float, progress: Float): Float =
    360F * sliceLength * progress / totalLength
