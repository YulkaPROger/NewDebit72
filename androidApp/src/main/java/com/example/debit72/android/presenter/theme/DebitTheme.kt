package com.example.debit72.android.presenter.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember


@Composable
fun DebitTheme(
    spaces: DebitSpacing = DebitTheme.spacing,
    typography: DebitTypography = DebitTheme.typography,
    colors: DebitColors = DebitTheme.colors,
    elevation: DebitElevation = DebitTheme.elevation,
    shape: DebitShape = DebitTheme.shape,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val rememberedColors = remember { colors.copy() }.apply { updateColorsFrom(colors) }
    CompositionLocalProvider(
        LocalDebitColors provides rememberedColors,
        LocalDebitTypography provides typography,
        LocalDebitElevation provides elevation,
        LocalDebitSpacing provides spacing,
        LocalDebitShape provides shape,
        content = content
    )
}

object DebitTheme {
    val colors: DebitColors
        @Composable
        get() = LocalDebitColors.current
    val typography: DebitTypography
        @Composable
        get() = LocalDebitTypography.current
    val elevation: DebitElevation
        @Composable
        get() = LocalDebitElevation.current
    val spacing: DebitSpacing
        @Composable
        get() = LocalDebitSpacing.current
    val shape: DebitShape
        @Composable
        get() = LocalDebitShape.current
}