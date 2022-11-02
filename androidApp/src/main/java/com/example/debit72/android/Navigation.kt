package com.example.debit72.android

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.debit72.android.presenter.home.HomeScreen
import com.example.debit72.android.presenter.registry_ip.RegistryIP
import com.example.debit72.android.presenter.more.MoreScreen
import com.example.debit72.android.presenter.registry_ip.FullIPScreen
import com.example.debit72.android.presenter.service.ServiceScreen


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Main.route) {
        composable(NavigationItem.Main.route) {
            HomeScreen(navController)
        }
        composable(NavigationItem.Service.route) {
            ServiceScreen(navController)
        }
        composable(NavigationItem.More.route) {
            MoreScreen(navController)
        }
        composable("Registry IP") {
            RegistryIP(navController)
        }
        composable(
            "fullIP/{number}"
        ) { backStackEntry ->
            FullIPScreen(backStackEntry.arguments?.getString("number"))
        }
//        composable("createObject") { CreateObjectScreen(navController) }
//        composable("choiceService") {
//            ChoiceService(navController)
//        }
//        composable("searchMaterialScreen"){
//            SearchMaterialScreen(navController)
//        }
    }
}


