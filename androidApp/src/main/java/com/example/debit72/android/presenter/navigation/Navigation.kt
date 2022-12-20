package com.example.debit72.android.presenter.navigation

import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.debit72.android.presenter.home.HomeScreen
import com.example.debit72.android.presenter.more.MoreScreen
import com.example.debit72.android.presenter.service.ServiceScreen
import com.example.debit72.android.presenter.service.arrested_auto.ArrestedAutoScreen
import com.example.debit72.android.presenter.service.arrested_property.ArrestedPropertyScreen
import com.example.debit72.android.presenter.service.auto.AutoScreen
import com.example.debit72.android.presenter.service.auto_from_number.AutoNumberScreen
import com.example.debit72.android.presenter.service.claimants.ClaimantsScreen
import com.example.debit72.android.presenter.service.claimants_rosp.ClaimantsOnTheRospScreen
import com.example.debit72.android.presenter.service.registry_ip.FullIPScreen
import com.example.debit72.android.presenter.service.registry_ip.RegistryIP
import com.example.debit72.android.presenter.service.sir.SirScreen
import com.example.debit72.android.presenter.service.spr.SprScreen
import com.example.debit72.android.presenter.additional_service.ReportErrorScreen


@Composable
fun Navigation(
    navController: NavHostController,
    showSnackbar: (String, SnackbarDuration) -> Unit
) {
    NavHost(navController, startDestination = NavigationItem.Main.route) {
        composable(NavigationItem.Main.route) {
            HomeScreen(navController)
        }
        composable(NavigationItem.Service.route) {
            ServiceScreen(navController)
        }
        composable(NavigationItem.More.route) {
            MoreScreen(navController, showSnackbar = showSnackbar)
        }
        composable(NameRoute.RegistryIPRoute.route) {
            RegistryIP(navController)
        }
        composable(
            "fullIP/{number}"
        ) { backStackEntry ->
            FullIPScreen(backStackEntry.arguments?.getString("number"))
        }
        composable(NameRoute.SprRoute.route) {
            SprScreen(navController)
        }
        composable(NameRoute.ClaimantsRoute.route) {
            ClaimantsScreen(navController)
        }
        composable(NameRoute.ClaimantsOnRospRoute.route) {
            ClaimantsOnTheRospScreen(navController)
        }
        composable(NameRoute.AutoRoute.route) {
            AutoScreen(navController)
        }
        composable(NameRoute.ArrestedAutoRoute.route) {
            ArrestedAutoScreen(navController)
        }
        composable(NameRoute.ArrestedPropertyRoute.route) {
            ArrestedPropertyScreen(navController)
        }
        composable(NameRoute.SirRoute.route) {
            SirScreen(navController)
        }
        composable(NameRoute.AutoFromNumberRoute.route) {
            AutoNumberScreen(navController)
        }
        composable(NameRoute.ReportErrorRoute.route) {
            ReportErrorScreen(navController)
        }



//        composable("createObject") { CreateObjectScreen(navController) }
//        composable("choiceService") {
//            ChoiceService(navController)
//        }
    }
}

sealed class NameRoute(
    val route: String,
    val params: String? = null
) {
    object ArrestedAutoRoute : NameRoute("arrested_auto")
    object ArrestedPropertyRoute : NameRoute("arrested_property")
    object AutoRoute : NameRoute("auto")
    object SprRoute : NameRoute("SPR screen")
    object ClaimantsRoute : NameRoute("claimants")
    object ClaimantsOnRospRoute: NameRoute("claimants_on_the_rosp")
    object RegistryIPRoute: NameRoute("Registry IP")
    object SirRoute: NameRoute("sir")
    object AutoFromNumberRoute: NameRoute("auto_from_number")
    object ReportErrorRoute: NameRoute("error_report")
    data class Ip(val param: String? = null): NameRoute("fullIP", param)

}


