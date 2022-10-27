package com.example.debit72.android.presenter.service

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.debit72.android.R
import com.example.debit72.android.presenter.theme.DebitTheme

@Composable
fun ServiceScreen(navController: NavHostController) {
    val width = LocalConfiguration.current.screenWidthDp
    val widthLongButton = (width * 0.6).dp
    val widthShortButton = (width * 0.3).dp
    val heightBanner = (width / 4).dp
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {

        item {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Тюмень", style = DebitTheme.typography.titleMedium20.copy(
                        color = DebitTheme.colors.text
                    )
                )
                Icon(
                    imageVector = Icons.Rounded.ArrowForwardIos,
                    contentDescription = "Arrow Forward Ios",
                    tint = DebitTheme.colors.text
                )
            }
        }
        item {
            ClaimantsButton(widthLongButton, widthShortButton, heightBanner, navController)
            AutoHouseAndIPButton(widthLongButton, widthShortButton, heightBanner, navController)
            SprAndSirWork(widthLongButton, widthShortButton, heightBanner, navController)
            AutoButton(widthLongButton, widthShortButton, heightBanner, navController)
        }
    }
}

@Composable
fun SprAndSirWork(
    widthLongButton: Dp,
    widthShortButton: Dp,
    heightBanner: Dp,
    navController: NavHostController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .width(widthShortButton)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.sir),
                contentDescription = "count icon",
                modifier = Modifier
                    .width(widthShortButton)
                    .height(heightBanner)
                    .align(Alignment.BottomEnd),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(id = R.string.sir_work),
                style = DebitTheme.typography.body12.copy(
                    color = DebitTheme.colors.text
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
        Box(
            modifier = Modifier
                .width(widthLongButton)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.spr),
                contentDescription = "count icon",
                modifier = Modifier
                    .width(widthLongButton)
                    .height(heightBanner)
                    .align(Alignment.BottomEnd),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(id = R.string.spr_work),
                style = DebitTheme.typography.titleMedium20.copy(
                    color = DebitTheme.colors.text
                ),

                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun AutoButton(
    widthLongButton: Dp,
    widthShortButton: Dp,
    heightBanner: Dp,
    navController: NavHostController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Box(
            modifier = Modifier
                .width(widthLongButton)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.search_auto_rt),
                contentDescription = "count icon",
                modifier = Modifier
                    .width(widthLongButton)
                    .height(heightBanner)
                    .align(Alignment.BottomEnd),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(id = R.string.search_auto_real_time),
                style = DebitTheme.typography.titleMedium20.copy(
                    color = DebitTheme.colors.text
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
        Box(
            modifier = Modifier
                .width(widthShortButton)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.auto_button),
                contentDescription = "count icon",
                modifier = Modifier
                    .width(widthShortButton)
                    .height(heightBanner)
                    .align(Alignment.BottomEnd),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(id = R.string.auto),
                style = DebitTheme.typography.body12.copy(
                    color = DebitTheme.colors.text
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun AutoHouseAndIPButton(
    widthLongButton: Dp,
    widthShortButton: Dp,
    heightBanner: Dp,
    navController: NavHostController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .width(widthShortButton)
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    navController.navigate("Registry IP")
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ip),
                contentDescription = "count icon",
                modifier = Modifier
                    .width(widthShortButton)
                    .height(heightBanner)
                    .align(Alignment.BottomEnd),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(id = R.string.ip_register),
                style = DebitTheme.typography.body12.copy(
                    color = DebitTheme.colors.text
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
        Box(
            modifier = Modifier
                .width(widthShortButton)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.auto),
                contentDescription = "count icon",
                modifier = Modifier
                    .width(widthShortButton)
                    .height(heightBanner)
                    .align(Alignment.BottomEnd),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(id = R.string.arrested_cars),
                style = DebitTheme.typography.body12.copy(
                    color = DebitTheme.colors.text
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
        Box(
            modifier = Modifier
                .width(widthShortButton)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.hous),
                contentDescription = "count icon",
                modifier = Modifier
                    .width(widthShortButton)
                    .height(heightBanner)
                    .align(Alignment.BottomEnd),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(id = R.string.arrested_property),
                style = DebitTheme.typography.body12.copy(
                    color = DebitTheme.colors.text
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun ClaimantsButton(
    widthLongButton: Dp,
    widthShortButton: Dp,
    heightBanner: Dp,
    navController: NavHostController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .width(widthLongButton)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.claimants_on_rosp),
                contentDescription = "count icon",
                modifier = Modifier
                    .width(widthLongButton)
                    .height(heightBanner)
                    .align(Alignment.BottomEnd),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(id = R.string.claimants_on_rosp),
                style = DebitTheme.typography.titleMedium20.copy(
                    color = DebitTheme.colors.text
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
        Box(
            modifier = Modifier
                .width(widthShortButton)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.claimants),
                contentDescription = "count icon",
                modifier = Modifier
                    .width(widthShortButton)
                    .height(heightBanner)
                    .align(Alignment.BottomEnd),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(id = R.string.claimants),
                style = DebitTheme.typography.body12.copy(
                    color = DebitTheme.colors.text
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}