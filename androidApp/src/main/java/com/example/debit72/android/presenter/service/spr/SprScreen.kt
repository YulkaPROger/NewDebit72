package com.example.debit72.android.presenter.service.spr

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PriorityHigh
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.debit72.android.R
import com.example.debit72.android.presenter.theme.DebitTheme.colors
import com.example.debit72.android.presenter.theme.typography
import com.example.debit72.android.widgets.ArrowBackWithSearch
import model.Spr

@Composable
fun SprScreen(navController: NavHostController) {
    val store: SprStore = viewModel(factory = SprStoreFactory(LocalContext.current))
    val state = store.observeState().collectAsState().value
    val modifier = Modifier.fillMaxWidth()
    var query by remember {
        mutableStateOf("")
    }

    var list: List<Spr>? by remember { mutableStateOf(null) }
    var error: String? by remember {
        mutableStateOf(null)
    }
    when (state) {
        is SprStore.State.Data -> {
            list = state.spr
            error = ""
        }
        is SprStore.State.LoadingError -> {
            error = state.error
            list = emptyList()
        }
        else -> {}
    }
    LaunchedEffect(key1 = Unit) {
        when (state) {
            is SprStore.State.Data -> {
                query = state.query ?: ""
            }
            else -> {}
        }
    }

    LaunchedEffect(query) {
        if (query.length >= 2) {
            store.dispatch(SprStore.Action.Search(query))
        } else store.dispatch(SprStore.Action.ClearSpr)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ArrowBackWithSearch(
            navController = navController,
            query = query,
            error = error,
            onChangeText = { query = it },
            count = list?.size
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 16.dp),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            list?.forEach {
                item {
                    CardSpr(spr = it, navController)
                }
            }
        }
    }
}

@Composable
fun CardSpr(spr: Spr, navController: NavHostController) {
    val modifier = Modifier.fillMaxWidth()
    Surface(
        modifier = modifier
            .clip(
                RoundedCornerShape(8.dp)
            )
            .clickable {
                navController.navigate("fullSpr/${spr.number}")
            },
        color = colors.cardColor
    ) {
        Column(modifier = modifier.padding(8.dp)) {
            Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
                Text(
                    text = spr.claimant, style =
                    typography.bodyNormal18.copy(
                        color = colors.onSecondary
                    )
                )
            }
            Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Column(modifier = Modifier.weight(4f)) {
                    Text(
                        text = stringResource(id = R.string.debtors, spr.naKogo),
                        style = typography.body16.copy(
                            color = colors.text
                        )
                    )
                }
                Column(modifier = Modifier.weight(3f)) {
                    Text(
                        text = stringResource(id = R.string.date, spr.submissionDate),
                        style = typography.body16.copy(
                            color = colors.text
                        )
                    )
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier) {
                        Text(
                            text = stringResource(id = R.string.court, spr.court),
                            style = typography.body16.copy(
                                color = colors.text
                            )
                        )
                        if (spr.currentCourt.isNotEmpty() && spr.currentCourt != spr.court)
                            Icon(
                                imageVector = Icons.Rounded.PriorityHigh,
                                contentDescription = "Priority High",
                                tint = colors.error,
                                modifier = Modifier.size(24.dp)
                            )
                    }
                    if (spr.currentCourt.isNotEmpty() && spr.currentCourt != spr.court) {
                        Text(
                            text = stringResource(
                                id = R.string.current_court,
                                spr.currentCourt
                            ),
                            style = typography.body16.copy(
                                color = colors.text
                            )
                        )

                    }
                }
            }
            Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
                Text(
                    text = spr.address,
                    style = typography.body16.copy(
                        color = colors.text
                    )
                )
            }
        }
    }
}