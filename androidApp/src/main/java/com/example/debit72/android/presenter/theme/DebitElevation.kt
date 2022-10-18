package com.example.debit72.android.presenter.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Immutable
data class DebitElevation(
    val elevation01: Dp,
    val elevation03: Dp,
    val elevation04: Dp,
    val elevation06: Dp,
    val elevation08: Dp,
    val elevation16: Dp,
)

val LocalConsultantElevation = staticCompositionLocalOf<DebitElevation> {
    error("No colors provided")
}

val elevation = DebitElevation(
    elevation01 = 1.dp,
    elevation03 = 3.dp,
    elevation04 = 4.dp,
    elevation06 = 6.dp,
    elevation08 = 8.dp,
    elevation16 = 16.dp
)