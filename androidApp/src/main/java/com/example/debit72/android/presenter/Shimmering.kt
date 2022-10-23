package com.example.debit72.android.presenter

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

private val ShimmerProvider = compositionLocalOf { false }

@Composable
fun Shimmering(isVisible: Boolean, content: @Composable () -> Unit) {
    CompositionLocalProvider(
        ShimmerProvider.provides(isVisible),
        content = content
    )
}

@Composable
fun isShimmering() = ShimmerProvider.current

@Composable
fun Modifier.shimmering(isVisible: Boolean? = null, round: Dp = 4.dp): Modifier =
    if (isVisible == true || ShimmerProvider.current) {
        this
            .placeholder(
                visible = true,
                color = Color.Black.copy(alpha = 0.12f),
                shape = RoundedCornerShape(round),
                highlight = PlaceholderHighlight.shimmer(
                    highlightColor = Color.White,
                )
            )
    } else {
        this
    }

