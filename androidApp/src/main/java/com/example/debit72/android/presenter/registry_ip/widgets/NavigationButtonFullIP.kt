package com.example.debit72.android.presenter.registry_ip.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.debit72.android.presenter.shimmering
import com.example.debit72.android.presenter.theme.DebitTheme
import com.example.debit72.android.presenter.theme.DebitTheme.colors

@Composable
fun NavigationButtonFullIP(
    name: String,
    width: Int = LocalConfiguration.current.screenWidthDp,
    widthCard: Double = 2.5
) {
    Column(
        modifier = Modifier
            .size((width / widthCard).dp, (width / 2.6).dp)
            .padding(8.dp)
            .border(
                width = 2.dp,
                color = DebitTheme.colors.onSecondary,
                shape = RoundedCornerShape(16.dp)
            ),
        verticalArrangement = Arrangement.Bottom

    ) {
        Text(
            text = name,
            modifier = Modifier
                .padding(8.dp)
                .shimmering(),
            style = DebitTheme.typography.body16.copy(
                color = colors.text
            ),
        )
    }
}