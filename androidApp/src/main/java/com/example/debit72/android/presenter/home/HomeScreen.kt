package com.example.debit72.android.presenter.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.debit72.android.presenter.home.widgets.AvatarRow
import com.example.debit72.android.presenter.home.widgets.ColumnData
import com.example.debit72.android.presenter.home.widgets.StoriesRow

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
            item {
                AvatarRow()
            }
            item {
                StoriesRow(navController)
            }
            item {
                ColumnData()
            }
        }
    }
}