package com.example.debit72.android.presenter.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.debit72.android.presenter.home.widgets.AutoNumber
import com.example.debit72.android.presenter.home.widgets.AvatarRow
import com.example.debit72.android.presenter.home.widgets.ColumnData
import com.example.debit72.android.presenter.home.widgets.StoriesRow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
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