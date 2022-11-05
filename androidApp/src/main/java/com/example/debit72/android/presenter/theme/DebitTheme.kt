package com.example.debit72.android.presenter.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.debit72.android.data_store.UserSettings


@Composable
fun DebitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val currentColor = remember { if (darkTheme) baseDarkPalette else baseLightPalette }
    val rememberedColors = remember { currentColor.copy() }.apply { updateColorsFrom(currentColor) }

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