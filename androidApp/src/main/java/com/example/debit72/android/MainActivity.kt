package com.example.debit72.android

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.debit72.android.presenter.theme.DebitTheme
import com.example.debit72.datastore.UserSettingsRepository
import com.example.debit72.datastore.getDataStore

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //val vm = mainViewModel(LocalContext.current)
            DebitTheme(

            ) {
                MainScreen()
            }
        }
    }

//    @Composable
//    fun mainViewModel(context: Context) = viewModel {
//        DebitViewModel(UserSettingsRepository(getDataStore(context)))
//    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        backgroundColor = DebitTheme.colors.background
    ) {
        it.calculateBottomPadding()
        Navigation(navController)
    }
}
