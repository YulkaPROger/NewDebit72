package com.example.debit72.android

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.debit72.android.presenter.home.HomeScreen
import com.example.debit72.android.presenter.home.more.MoreScreen
import com.example.debit72.android.presenter.service.ServiceScreen


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Main.route) {
        composable(NavigationItem.Main.route) {
            HomeScreen(navController)
        }
        composable(NavigationItem.Service.route) {
            ServiceScreen()
        }
        composable(NavigationItem.More.route) {
            MoreScreen()
        }
//        composable("createObject") { CreateObjectScreen(navController) }
//        composable(
//            "detailObject/{idObject}"
//        ) { backStackEntry ->
//            DetailObjectScreen(navController, backStackEntry.arguments?.getString("idObject"))
//        }
//        composable("choiceService") {
//            ChoiceService(navController)
//        }
//        composable("searchMaterialScreen"){
//            SearchMaterialScreen(navController)
//        }
    }
}


