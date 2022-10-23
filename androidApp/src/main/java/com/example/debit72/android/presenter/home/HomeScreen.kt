package com.example.debit72.android.presenter.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.size.Size
import com.example.debit72.android.presenter.home.widgets.AutoNumber
import com.example.debit72.android.presenter.home.widgets.AvatarRow
import com.example.debit72.android.presenter.home.widgets.ColumnData
import com.example.debit72.android.presenter.home.widgets.StoriesRow
import com.example.debit72.android.presenter.theme.DebitTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        val width = LocalConfiguration.current.screenWidthDp
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