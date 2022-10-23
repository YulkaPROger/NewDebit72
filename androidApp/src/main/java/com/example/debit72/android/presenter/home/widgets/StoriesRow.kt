package com.example.debit72.android.presenter.home.widgets

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.debit72.android.R

@Composable
fun StoriesRow() {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val storisWidth = Modifier.size((screenWidth / 3.5).dp)
    LazyRow() {
        items(1) {
            NavigationButton(
                painterResource = R.drawable.background1,
                name = R.string.ip_register,
                modifier = storisWidth,
                {}
            )
            NavigationButton(
                painterResource = R.drawable.background2,
                name = R.string.auto,
                modifier = storisWidth,
                {}
            )
            NavigationButton(
                painterResource = R.drawable.background3,
                name = R.string.spr_work,
                modifier = storisWidth,
                {}
            )
            NavigationButton(
                painterResource = R.drawable.background4,
                name = R.string.claimants,
                modifier = storisWidth,
                {}
            )
            NavigationButton(
                painterResource = R.drawable.background5,
                name = R.string.search_auto_real_time,
                modifier = storisWidth,
                {}
            )
            NavigationButton(
                painterResource = R.drawable.background6,
                name = R.string.arrested_cars,
                modifier = storisWidth,
                {}
            )
            NavigationButton(
                painterResource = R.drawable.background7,
                name = R.string.arrested_property,
                modifier = storisWidth,
                {}
            )
            NavigationButton(
                painterResource = R.drawable.background9,
                name = R.string.claimants_on_ROSP,
                modifier = storisWidth,
                {}
            )
        }
    }
}

@Preview
@Composable
fun StoriesRowPreview() {
    StoriesRow()
}
