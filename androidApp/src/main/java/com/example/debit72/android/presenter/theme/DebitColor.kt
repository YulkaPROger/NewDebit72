package com.example.debit72.android.presenter.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class DebitColors(
    val yellowPrimary: Color,
    val greenPrimary: Color,
    val linkColor: Color,
    val promoPrice: Color,
    val certificate: Color,
    val promo: Color,
    val delivery: Color,
    val tinting: Color,
    val cut: Color,
    val orderInfo: Color,
    val black: Color = Color.Black,
    val black87: Color = Color.Black.copy(alpha = 0.87f),
    val black60: Color = Color.Black.copy(alpha = 0.6f),
    val black54: Color = Color.Black.copy(alpha = 0.54f),
    val black12: Color = Color.Black.copy(alpha = 0.12f),
    val gray900: Color = Color(0xFF212121),
    val gray800: Color = Color(0xFF424242),
    val gray700: Color = Color(0xFF616161),
    val gray600: Color = Color(0xFF757575),
    val gray500: Color = Color(0xFF9E9E9E),
    val gray400: Color = Color(0xFFBDBDBD),
    val gray300: Color = Color(0xFFE0E0E0),
    val gray200: Color = Color(0xFFEEEEEE),
    val gray100: Color = Color(0xFFF5F5F5),
    val gray50: Color = Color(0xFFFAFAFA),
    val darkGreen: Color = Color(0xFF1E4420),
    val white: Color = Color.White,
    val colorControlActivated: Color = yellowPrimary,
    val error: Color
    )


val LocalConsultantColors = staticCompositionLocalOf<DebitColors> {
    error("No colors provided")
}


val baseLightPalette = DebitColors(
    yellowPrimary = Color(0xFFFFCF4C),
    greenPrimary = Color(0xFF3DA442),
    linkColor = Color(0xFF2196F3),
    promoPrice = Color(0xFFFF5252),
    certificate = Color(0xFF779441),
    promo = Color(0xFFF5B42C),
    delivery = Color(0xFF39667B),
    tinting = Color(0xFF465DA1),
    cut = Color(0xFF969755),
    orderInfo = Color(0xFFD7BD72),
    error = Color(0xFFFF5252)
)


val baseDarkPalette = DebitColors(
    yellowPrimary = Color(0xFFFFCF4C),
    greenPrimary = Color(0xFF3DA442),
    linkColor = Color(0xFF2196F3),
    promoPrice = Color(0xFFFF5252),
    certificate = Color(0xFF779441),
    promo = Color(0xFFF5B42C),
    delivery = Color(0xFF39667B),
    tinting = Color(0xFF465DA1),
    cut = Color(0xFF969755),
    orderInfo = Color(0xFFD7BD72),
    error = Color(0xFFFF5252)
)