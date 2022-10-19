package com.example.debit72.android.presenter.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.debit72.android.presenter.theme.DebitTheme.colors

@Immutable
data class DebitTypography(
    val titleMedium16: TextStyle,
    val bodyLarge16: TextStyle,
    val bodyNormal14: TextStyle,
    val bodySmall12bigText: TextStyle,
    val bodySmall12: TextStyle,
    val bodyVerySmall10: TextStyle,
    val autoNumberStyle: TextStyle

)

val LocalConsultantTypography = staticCompositionLocalOf<DebitTypography> {
    error("No colors provided")
}

val typography: DebitTypography = DebitTypography(
    titleMedium16 = TextStyle(
        fontSize = 16.sp,
        letterSpacing = 0.1.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = DebitFont,
    ),
    bodyLarge16 = TextStyle(
        fontSize = 16.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = DebitFont
    ),
    bodyNormal14 = TextStyle(
        fontSize = 14.sp,
        letterSpacing = 0.25.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = DebitFont
    ),
    bodySmall12bigText = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = DebitFont
    ),
    bodySmall12 = TextStyle(
        fontSize = 12.sp,
        letterSpacing = 0.15.sp,
        fontWeight = FontWeight.Normal
    ),
    bodyVerySmall10 = TextStyle(
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

