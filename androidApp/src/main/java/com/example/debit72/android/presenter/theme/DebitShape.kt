package com.example.debit72.android.presenter.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
data class DebitShape(
    val smallShape2: CornerBasedShape,
    val defaultShape4: CornerBasedShape,
    val mediumShape8: CornerBasedShape,
    val largeShape16: CornerBasedShape,
    val bottomSheetShape: CornerBasedShape,
    )

val LocalDebitShape = staticCompositionLocalOf<DebitShape> {
    shape
}

val shape = DebitShape(
    smallShape2 = RoundedCornerShape(2.dp),
    defaultShape4 = RoundedCornerShape(4.dp),
    mediumShape8 = RoundedCornerShape(8.dp),
    largeShape16 = RoundedCornerShape(16.dp),
    bottomSheetShape = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp,
        bottomEnd = 0.dp,
        bottomStart = 0.dp
    ),
)