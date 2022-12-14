package com.example.debit72.android.presenter.home.widgets

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.debit72.android.presenter.theme.DebitTheme

@Composable
fun NavigationButton(
    @DrawableRes painterResource: Int,
    @StringRes name: Int,
    modifier: Modifier,
    onClick: () -> Unit
) {
    val width = LocalConfiguration.current.screenWidthDp
    Box(
        modifier = Modifier
            .size((width / 3.5).dp)
            .padding(8.dp)
            .border(
                width = 2.dp,
                color = DebitTheme.colors.surface,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 4.dp,
                color = DebitTheme.colors.black,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable {
                onClick.invoke()
            }

    ) {
        Image(
            painter = painterResource(id = painterResource),
            contentDescription = "",
            modifier = Modifier
                .size((width / 3.5).dp)
                .clip(
                    RoundedCornerShape(16.dp)
                ),
            contentScale = ContentScale.Crop
        )
        Text(
            text = stringResource(id = name),
            modifier = Modifier
                .padding(bottom = 12.dp, end = 12.dp, start = 12.dp)
                .align(Alignment.BottomEnd),
            maxLines = 4,
            style = DebitTheme.typography.body12,
            textAlign = TextAlign.End
        )
    }
}