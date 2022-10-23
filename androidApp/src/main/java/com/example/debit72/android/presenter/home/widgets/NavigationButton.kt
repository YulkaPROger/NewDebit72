package com.example.debit72.android.presenter.home.widgets

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
            text = "Название\nбаннера",
            modifier = Modifier
                .padding(bottom = 12.dp, end = 12.dp)
                .align(Alignment.BottomEnd),
            maxLines = 2,
            style = DebitTheme.typography.body12,
            textAlign = TextAlign.End
        )
    }
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceEvenly,
//        modifier = modifier
//            .padding(6.dp)
//            .border(
//                width = 3.dp,
//                color = colors.onPrimary,
//                shape = RoundedCornerShape(8.dp)
//            )
//    ) {
//        Text(
//            text = stringResource(id = name),
//            textAlign = TextAlign.Center,
//            maxLines = 2,
//            overflow = TextOverflow.Ellipsis,
//            modifier = Modifier.padding(4.dp)
//        )
//    }
}