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
fun NavigationButtonFullIP(
    title: String,
    text1: String,
    text2: String? = null,
    width: Int = LocalConfiguration.current.screenWidthDp,
    alignment: TextAlign = TextAlign.Left,
    style: TextStyle = DebitTheme.typography.body16.copy(
        color = colors.text
    )
) {
    Column(
        modifier = Modifier
            .size((width / 1.6).dp, (width / 2.2).dp)
            .padding(8.dp)
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
                .padding(8.dp)
                .shimmering(),
            style = DebitTheme.typography.bodyNormal18.copy(
                color = colors.onSecondary,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center
        )
        Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
            Text(
                text = text1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .shimmering(),
                style = style,
                textAlign = alignment
            )
            if (text2 != null)
                Text(
                    text = text2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .shimmering(),
                    style = style,
                    textAlign = alignment
                )
        }
    }
}