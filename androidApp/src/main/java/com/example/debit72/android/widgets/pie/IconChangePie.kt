package com.example.debit72.android.widgets.pie

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.debit72.android.R

@Composable
fun IconChangePie(isPieChart: Boolean) {
    Image(
        painterResource(id = if (!isPieChart) R.drawable.pie_chart else R.drawable.bar_chart),
        contentDescription = "",
        modifier = Modifier.clickable {
        })
}