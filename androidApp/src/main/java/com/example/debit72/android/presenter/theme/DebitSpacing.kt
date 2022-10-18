package com.example.debit72.android.presenter.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class DebitSpacing(
    val spacing04: Dp,
    val spacing08: Dp,
    val spacing16: Dp,
)

val LocalConsultantSpacing = staticCompositionLocalOf<DebitSpacing> {
    error("No spacing provided")
}

val spacing = DebitSpacing(
    spacing04 = 4.dp,
    spacing08 = 8.dp,
    spacing16 = 16.dp
)