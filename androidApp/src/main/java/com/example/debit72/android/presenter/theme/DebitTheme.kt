package com.rosa.consultant.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.example.debit72.android.presenter.theme.*


@Composable
fun DebitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = when (darkTheme) {
        true -> {
            baseLightPalette
        }
        else -> {
            baseDarkPalette
        }
    }
    val typography = typography.copy(
        bodyNormal14 = typography.bodyNormal14.copy(
            color = colors.gray400
        ),
        bodyLarge16 = typography.bodyLarge16.copy(
            color = colors.gray400
        ),
        titleMedium16 = typography.titleMedium16.copy(
            color = colors.gray400
        ),
        bodySmall12 = typography.bodySmall12.copy(
            color = colors.gray400
        ),
        bodySmall12bigText = typography.bodySmall12bigText.copy(
            color = colors.gray400
        ),
    )

    CompositionLocalProvider(
        LocalConsultantColors provides colors,
        LocalConsultantTypography provides typography,
        LocalConsultantElevation provides elevation,
        LocalConsultantSpacing provides spacing,
        LocalConsultantShape provides shape,
        content = content
    )

}

object ConsultantTheme {
    val colors: DebitColors
        @Composable
        get() = LocalConsultantColors.current
    val typography: DebitTypography
        @Composable
        get() = LocalConsultantTypography.current
    val elevation: DebitElevation
        @Composable
        get() = LocalConsultantElevation.current
    val spacing: DebitSpacing
        @Composable
        get() = LocalConsultantSpacing.current
    val shape: DebitShape
        @Composable
        get() = LocalConsultantShape.current
}