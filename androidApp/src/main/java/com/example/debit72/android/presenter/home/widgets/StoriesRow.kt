package com.example.debit72.android.presenter.home.widgets

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.debit72.android.R
import com.example.debit72.android.presenter.navigation.NameRoute

@Composable
fun StoriesRow(navController: NavHostController) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val storisWidth = Modifier.size((screenWidth / 3.5).dp)
    LazyRow() {
        items(1) {
            NavigationButton(
                painterResource = R.drawable.background1,
                name = R.string.report_error,
                modifier = storisWidth
            ) {
                navController.navigate(NameRoute.ReportErrorRoute.route)
            }
            NavigationButton(
                painterResource = R.drawable.background2,
                name = R.string.request_court_work,
                modifier = storisWidth,
            ){
                navController.navigate(NameRoute.RequestCourtWorkRoute.route)
            }
            NavigationButton(
                painterResource = R.drawable.background3,
                name = R.string.catalog_rosp,
                modifier = storisWidth
            ){
                navController.navigate(NameRoute.CatalogRospRoute.route)
            }
            NavigationButton(
                painterResource = R.drawable.background4,
                name = R.string.catalog_court,
                modifier = storisWidth
            ){
                navController.navigate(NameRoute.CatalogCourtRoute.route)
            }
            NavigationButton(
                painterResource = R.drawable.background5,
                name = R.string.tasks,
                modifier = storisWidth
            ){
                navController.navigate(NameRoute.TasksRoute.route)
            }
            NavigationButton(
                painterResource = R.drawable.background6,
                name = R.string.catalog_spi,
                modifier = storisWidth,
            ){
                navController.navigate(NameRoute.CatalogSpiRoute.route)
            }
//            NavigationButton(
//                painterResource = R.drawable.background7,
//                name = R.string.arrested_property,
//                modifier = storisWidth,
//                {}
//            )
//            NavigationButton(
//                painterResource = R.drawable.background9,
//                name = R.string.claimants_on_ROSP,
//                modifier = storisWidth,
//                {}
//            )
        }
    }
}

@Preview
@Composable
fun StoriesRowPreview() {
    StoriesRow(navController = rememberNavController())
}
