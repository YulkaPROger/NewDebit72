package com.example.debit72.android.presenter.service.registry_ip.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.debit72.android.presenter.theme.DebitTheme
import com.example.debit72.android.presenter.theme.DebitTheme.colors
import com.example.debit72.android.utils.shimmering

@Composable
fun StoriesIp(
    title: String,
    width: Int = LocalConfiguration.current.screenWidthDp,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .size((width / 1.6).dp, (width / 2.5).dp)
            .padding(top = 8.dp)
            .border(
                width = 1.dp,
                color = DebitTheme.colors.onSecondary,
                shape = RoundedCornerShape(16.dp)
            ),
    ) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .shimmering(),
            style = DebitTheme.typography.bodyNormal18.copy(
                color = colors.onSecondary,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center
        )
        content.invoke()
    }
}