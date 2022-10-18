package com.example.debit72.android.widgets

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun NavigationButton(
    imageVector: ImageVector,
    @StringRes name: Int,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        IconButton(onClick = { onClick.invoke() }) {
            Icon(
                imageVector = imageVector,
                contentDescription = stringResource(id = name),
                modifier = Modifier.size(50.dp)
            )
        }
        Text(text = stringResource(id = name), textAlign = TextAlign.Center)
    }
}