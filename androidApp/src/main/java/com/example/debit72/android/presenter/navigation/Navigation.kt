package com.example.debit72.android.presenter.navigation

import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.debit72.android.presenter.home.HomeScreen
import com.example.debit72.android.presenter.more.MoreScreen
import com.example.debit72.android.presenter.service.ServiceScreen
import com.example.debit72.android.presenter.service.claimants.ClaimantsScreen
import com.example.debit72.android.presenter.service.registry_ip.FullIPScreen
import com.example.debit72.android.presenter.service.registry_ip.RegistryIP
import com.example.debit72.android.presenter.service.spr.FullSprScreen
import com.example.debit72.android.presenter.service.spr.SprScreen


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
        composable("Registry IP") {
            RegistryIP(navController)
        }
        composable(
            "fullIP/{number}"
        ) { backStackEntry ->
            FullIPScreen(backStackEntry.arguments?.getString("number"))
        }
        composable("SPR screen") {
            SprScreen(navController)
        }
        composable(
            "fullSpr/{number}"
        ) { backStackEntry ->
            FullSprScreen(backStackEntry.arguments?.getString("number"))
        }
        composable("claimants") {
            ClaimantsScreen(navController)
        }
//        composable("createObject") { CreateObjectScreen(navController) }
//        composable("choiceService") {
//            ChoiceService(navController)
//        }
    }
}


