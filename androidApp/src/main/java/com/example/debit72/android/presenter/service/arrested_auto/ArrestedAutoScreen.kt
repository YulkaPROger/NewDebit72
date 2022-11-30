package com.example.debit72.android.presenter.service.arrested_auto

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DirectionsCar
import androidx.compose.material.icons.rounded.SelfImprovement
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.debit72.android.R
import com.example.debit72.android.presenter.theme.DebitTheme
import com.example.debit72.android.widgets.ArrowBackWithSearch
import model.OldArrestedAuto

@Composable
fun ArrestedAutoScreen(navController: NavHostController) {
    val store: ArrestedAutoStore =
        viewModel(factory = ArrestedAutoStoreFactory(LocalContext.current))
    val state = store.observeState().collectAsState().value
    val modifier = Modifier.fillMaxWidth()
    var query by remember {
        mutableStateOf("")
    }

    var list: List<OldArrestedAuto>? by remember { mutableStateOf(null) }
    var error: String? by remember {
        mutableStateOf(null)
    }
    var loader by remember {
        mutableStateOf(false)
    }
    when (state) {
        is ArrestedAutoStore.State.Data -> {
            list = state.filterAuto
            error = ""
            loader = false
        }
        is ArrestedAutoStore.State.LoadingError -> {
            error = state.error
            loader = false
        }
        is ArrestedAutoStore.State.Loading -> {
            loader = true
        }
        else -> {}
    }
    LaunchedEffect(key1 = Unit) {
        when (state) {
            is ArrestedAutoStore.State.Data -> {
                query = state.query ?: ""
            }
            else -> {}
        }
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(query) {
        if (lifecycleOwner.lifecycle.currentState != Lifecycle.State.STARTED)
            if (query.length > 2) {
                store.dispatch(ArrestedAutoStore.Action.Search(query))
            } else store.dispatch(ArrestedAutoStore.Action.Clear)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        ArrowBackWithSearch(
            navController = navController,
            query = query,
            error = error,
            onChangeText = {
                query = it
            },
            count = list?.size
        )
        if (loader) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = DebitTheme.colors.onSecondary)
            }
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 16.dp),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            list?.forEach {
                item {
                    CardAuto(auto = it, navController)
                }
            }
        }
    }
}

@Composable
fun CardAuto(auto: OldArrestedAuto, navController: NavHostController) {
    val modifier = Modifier.fillMaxWidth()
    val width = LocalConfiguration.current.screenWidthDp
    Surface(
        modifier = modifier
            .clip(
                RoundedCornerShape(8.dp)
            )
            .clickable {
            },
        color = DebitTheme.colors.cardColor
    ) {
        Column(modifier = modifier.padding(8.dp)) {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.SelfImprovement,
                    contentDescription = "person",
                    tint = DebitTheme.colors.onSecondary
                )
                Text(
                    text = auto.owner,
                    style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                )
            }
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.DirectionsCar,
                    contentDescription = "Directions Car",
                    tint = DebitTheme.colors.onSecondary
                )
                Column() {
                    Text(
                        text = stringResource(id = R.string.model, auto.modelTs),
                        style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                    )
                    Text(
                        text = stringResource(id = R.string.gos_number, auto.gosNumber),
                        style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                    )
                }
            }
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column {
                    Text(
                        text = stringResource(id = R.string.price, auto.price),
                        style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                    )
                    Text(
                        text = stringResource(
                            id = R.string.auto_date_arrested,
                            auto.dateArrestedTs
                        ),
                        style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                    )
                    Divider(
                        color = DebitTheme.colors.onSecondary,
                        modifier = Modifier.padding(4.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.storage, auto.storage),
                        style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                    )
                }
            }
            LazyRow(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                auto.ip.forEach { list ->
                    item {
                        Column(
                            modifier = Modifier
                                .width((width * 0.6).dp)
                                .border(
                                    width = 2.dp,
                                    color = DebitTheme.colors.onSecondary,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(8.dp)
                                .clickable {
                                    navController.navigate("fullIP/${list.number}")
                                },
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = list.regNumberIp,
                                style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                            )
                            Text(
                                text = list.claimant,
                                style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                            )
                            Text(
                                text = list.rosp,
                                style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                            )
                            Text(
                                text = stringResource(
                                    id = R.string.balance_owed_with_params,
                                    list.balanceOwed
                                ),
                                style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                            )
                            Text(
                                text = stringResource(id = R.string.spi, list.spi),
                                style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                            )
                        }

                    }
                }
            }

        }
    }
}