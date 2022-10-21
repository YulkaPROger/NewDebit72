package com.example.debit72.android.widgets

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.debit72.android.presenter.theme.DebitTheme.colors

@Composable
fun NavigationButton(
    imageVector: ImageVector,
    @StringRes name: Int,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .padding(6.dp)
            .border(
                width = 3.dp,
                color = colors.error,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        IconButton(onClick = { onClick.invoke() }) {
            Icon(
                imageVector = imageVector,
                contentDescription = stringResource(id = name),
                modifier = Modifier.size(50.dp)
            )
        }
        Text(
            text = stringResource(id = name),
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(4.dp)
        )
    }
}