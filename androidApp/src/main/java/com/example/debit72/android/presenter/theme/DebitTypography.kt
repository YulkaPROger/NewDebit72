package com.example.debit72.android.presenter.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class DebitTypography(
    val titleMedium20: TextStyle,
    val bodyLarge16: TextStyle,
    val bodyNormal18: TextStyle,
    val body16: TextStyle,
    val body12: TextStyle,
    val body10: TextStyle,
    val autoNumberStyle: TextStyle

)

val LocalDebitTypography = staticCompositionLocalOf<DebitTypography> {
    typography
}

val typography: DebitTypography = DebitTypography(
    titleMedium20 = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = DebitFont,
    ),
    bodyNormal18 = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = DebitFont
    ),
    bodyLarge16 = TextStyle(
        fontSize = 16.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = DebitFont
    ),
    body16 = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = DebitFont
    ),
    body12 = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
    ),
    body10 = TextStyle(
        fontSize = 10.sp,
        letterSpacing = 0.1.sp,
        fontWeight = FontWeight.Normal
    ),
    autoNumberStyle = TextStyle(
        fontSize = 32.sp,
        letterSpacing = 1.sp,
        fontWeight = FontWeight.Bold
    )
)

