package com.example.debit72.android

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.debit72.android.data_store.UserSettings
import com.example.debit72.android.presenter.navigation.BottomNavigationBar
import com.example.debit72.android.presenter.navigation.Navigation
import com.example.debit72.android.presenter.theme.DebitTheme
import com.example.debit72.android.utils.SnackbarDebitAppState
import com.example.debit72.android.utils.rememberSnackbarDebitAppState
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val dataStore = UserSettings(LocalContext.current)
            var themeState: Boolean by remember {
                mutableStateOf(true)
            }
            LaunchedEffect(dataStore.getBoolean(UserSettings.DARK_THEME)) {
                dataStore.getBoolean(UserSettings.DARK_THEME).collectLatest {
                    themeState = it
                }
            }
            DebitTheme(
                darkTheme = themeState
            ) {
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
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val appState: SnackbarDebitAppState =
        rememberSnackbarDebitAppState(navController = navController)
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        backgroundColor = DebitTheme.colors.background,
        scaffoldState = appState.scaffoldState
    ) {
        it.calculateBottomPadding()
        Navigation(navController,
            showSnackbar = { message, duration ->
                appState.showSnackbar(message = message, duration = duration)
            })
    }
}


