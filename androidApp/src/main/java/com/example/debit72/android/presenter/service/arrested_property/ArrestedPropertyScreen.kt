package com.example.debit72.android.presenter.service.arrested_property

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
import com.example.debit72.android.presenter.service.arrested_auto.PresenterArrestedAuto
import com.example.debit72.android.presenter.theme.DebitTheme
import com.example.debit72.android.widgets.ArrowBackWithSearch
import kotlinx.coroutines.delay
import model.OldArrestedProperty

@Composable
fun ArrestedPropertyScreen(navController: NavHostController) {
    val store: ArrestedPropertyStore =
        viewModel(factory = ArrestedPropertyStoreFactory(LocalContext.current))
    val state = store.observeState().collectAsState().value
    val modifier = Modifier.fillMaxWidth()
    var query by remember {
        mutableStateOf("")
    }

    var list: List<OldArrestedProperty>? by remember { mutableStateOf(null) }
    var error: String? by remember {
        mutableStateOf(null)
    }
    var loader by remember {
        mutableStateOf(false)
    }
    when (state) {
        is ArrestedPropertyStore.State.Data -> {
            list = state.filterProperties
            error = ""
            loader = false
        }
        is ArrestedPropertyStore.State.LoadingError -> {
            error = state.error
            loader = false
        }
        is ArrestedPropertyStore.State.Loading -> {
            loader = true
        }
        else -> {}
    }
    LaunchedEffect(key1 = Unit) {
        when (state) {
            is ArrestedPropertyStore.State.Data -> {
                query = state.query ?: ""
            }
            else -> {}
        }
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(query) {
        if (lifecycleOwner.lifecycle.currentState != Lifecycle.State.STARTED)
            if (query.length > 2) {
                store.dispatch(ArrestedPropertyStore.Action.Search(query))
            } else store.dispatch(ArrestedPropertyStore.Action.Clear)
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
                    CardProperty(
                        property = it,
                        navController
                    )
                }
            }
        }
    }
}

@Composable
fun CardProperty(property: OldArrestedProperty, navController: NavHostController) {
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
                Text(
                    text = property.property,
                    style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                )
            }
            Divider(color = DebitTheme.colors.onSecondary, modifier = Modifier.padding(4.dp))
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column() {
                    Text(
                        text = stringResource(id = R.string.price, property.price),
                        style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                    )
                    Text(
                        text = stringResource(id = R.string.date_arrested, property.dateArrested),
                        style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                    )
                    Text(
                        text = property.owner,
                        style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                    )
                }
            }
            Divider(color = DebitTheme.colors.onSecondary, modifier = Modifier.padding(4.dp))
            LazyRow(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                property.ip.forEach { list ->
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