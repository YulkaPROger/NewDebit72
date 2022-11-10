package com.example.debit72.android.presenter.theme

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

@Immutable
class DebitColors(
    text: Color,
    background: Color,
    cardColor: Color,
    success: Color,
    error: Color,
    isLight: Boolean,
    val primary: Color = Color(0xFFE91E63),
    val primaryVariant: Color = Color(0xFFC21858),
    val secondary: Color = Color(0xFF9C27B0),
    val secondaryVariant: Color = Color(0xFF5728B0),
    val onSurface: Color = Color(0xFF272AB0),
    val surface: Color = Color(0xFF276880),
    val onPrimary: Color = Color(0xFF57DCBE),
    val onSecondary: Color = Color(0xFF60C689),
    val onError: Color = Color.Red,
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
    val white: Color = Color.White
) {

    var text by mutableStateOf(text)
        private set

    var success by mutableStateOf(success)
        private set

    var error by mutableStateOf(error)
        private set

    var background by mutableStateOf(background)
        private set

    var isLight by mutableStateOf(isLight)
        private set

    var cardColor by mutableStateOf(cardColor)
        private set


    fun copy(
        primary: Color = this.primary,
        text: Color = this.text,
        background: Color = this.background,
        success: Color = this.success,
        error: Color = this.error,
        isLight: Boolean = this.isLight,
        cardColor: Color = this.cardColor
    ) = DebitColors(
        primary = primary,
        text = text,
        background = background,
        success = success,
        error = error,
        isLight = isLight,
        cardColor = cardColor
    )

    fun updateColorsFrom(other: DebitColors) {
        text = other.text
        success = other.success
        background = other.background
        error = other.error
        isLight = other.isLight
    }
}

val LocalDebitColors = staticCompositionLocalOf {
    baseDarkPalette
}

val baseLightPalette = DebitColors(
    text = Color.Black,
    background = Color.White,
    success = Color.Green,
    error = Color.Red,
    isLight = true,
    cardColor = Color(0xFF80CBC4)
)


val baseDarkPalette = DebitColors(
    text = Color(0xFFE0E0E0),
    background = Color(0xFF353535),
    success = Color.Green,
    error = Color.Red,
    isLight = false,
    cardColor = Color(0xFF424242)
)