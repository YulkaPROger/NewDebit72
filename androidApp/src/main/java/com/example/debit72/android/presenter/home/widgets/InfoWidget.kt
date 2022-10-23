package com.example.debit72.android.presenter.home.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.debit72.android.presenter.theme.DebitTheme

@Composable
fun InfoWidget() {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(150.dp)
            .background(DebitTheme.colors.gray900)
            .border(
                width = 2.dp,
                color = DebitTheme.colors.surface,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {

            Text(
                text = "Some text", style = DebitTheme.typography.bodyNormal18.copy(
                    color = DebitTheme.colors.text
                )
            )
        }
    }
}