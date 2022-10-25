package com.example.debit72.android.presenter.home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.debit72.SpaceXSDK
import com.example.debit72.android.presenter.home.widgets.AutoNumber
import com.example.debit72.android.presenter.home.widgets.AvatarRow
import com.example.debit72.android.presenter.home.widgets.ColumnData
import com.example.debit72.android.presenter.home.widgets.StoriesRow
import com.example.debit72.android.presenter.theme.DebitTheme
import com.example.debit72.database.DatabaseDriverFactory
import com.example.debit72.entity.IP

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val sdk = SpaceXSDK(DatabaseDriverFactory(LocalContext.current))
    val scope = rememberCoroutineScope()
    var list: List<IP>? by remember { mutableStateOf(null) }

    LaunchedEffect(1) {
        kotlin.runCatching {
            sdk.getAllIp(true)
        }.onSuccess {
            list = it
            Log.d("database", list.toString())
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        list?.let {
            for (i in it) {
                Log.d("database", i.toString())

                Text(
                    text = i.regNumberIP,
                    style = DebitTheme.typography.body14.copy(color = DebitTheme.colors.text)
                )
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            stickyHeader {
                AvatarRow()
            }
            item {
                AutoNumber()
            }
            item {
                StoriesRow()
            }
            item {
                ColumnData()
            }

        }
    }
}