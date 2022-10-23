package com.example.debit72.android.presenter.home.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.debit72.android.R

@Composable
fun AutoNumber() {
    val width = LocalConfiguration.current.screenWidthDp
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(2.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.number),
                    contentDescription = "number",
                    modifier = Modifier.width((width / 1.5).dp)
                )

            }
        }
    }
}