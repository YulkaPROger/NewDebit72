package com.example.debit72.android

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.debit72.android.presenter.theme.DebitTheme

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //val vm = mainViewModel(LocalContext.current)
            DebitTheme() {
                MainScreen()
            }
        }
        hideSystemUI()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun hideSystemUI() {
        window.insetsController?.apply {
            hide(WindowInsets.Type.statusBars())
            systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
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


