package com.example.debit72.android

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.debit72.android.UI.HomeScreen


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Materials.route) {
        composable(NavigationItem.Materials.route) {
            HomeScreen(navController)
        }
//        composable(NavigationItem.Services.route) {
//            ServicesScreen()
//        }
//        composable(NavigationItem.Objects.route) {
//            ObjectsScreen()
//        }
//        composable(NavigationItem.Profile.route) {
//            ProfileScreen()
//        }
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


