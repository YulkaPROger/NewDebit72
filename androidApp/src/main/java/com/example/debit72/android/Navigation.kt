package com.example.debit72.android

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.debit72.android.presenter.HomeScreen
import com.example.debit72.android.presenter.IPScreen
import com.example.debit72.android.presenter.ProfileScreen
import com.example.debit72.android.presenter.PropertyScreen


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            HomeScreen(navController)
        }
        composable(NavigationItem.Property.route) {
            PropertyScreen()
        }
        composable(NavigationItem.IP.route) {
            IPScreen()
        }
        composable(NavigationItem.Profile.route) {
            ProfileScreen()
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


