package com.example.debit72.android.presenter.service.auto

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DirectionsCar
import androidx.compose.material.icons.rounded.SelfImprovement
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.debit72.android.R
import com.example.debit72.android.presenter.theme.DebitTheme
import com.example.debit72.android.presenter.theme.DebitTheme.colors
import com.example.debit72.android.widgets.ArrowBackWithSearch

@Composable
fun AutoScreen(navController: NavHostController) {
    val store: AutoStore = viewModel(factory = AutoStoreFactory(LocalContext.current))
    val state = store.observeState().collectAsState().value
    val modifier = Modifier.fillMaxWidth()
    var query by remember {
        mutableStateOf("")
    }

    var list: List<PresenterAuto>? by remember { mutableStateOf(null) }
    var error: String? by remember {
        mutableStateOf(null)
    }
    when (state) {
        is AutoStore.State.Data -> {
            list = state.auto
            error = ""
        }
        is AutoStore.State.LoadingError -> {
            error = state.error
            list = emptyList()
        }
        else -> {}
    }
    LaunchedEffect(key1 = Unit) {
        when (state) {
            is AutoStore.State.Data -> {
                query = state.query ?: ""
            }
            else -> {}
        }
    }

    LaunchedEffect(query) {
        if (query.length > 2) {
            store.dispatch(AutoStore.Action.Search(query))
        } else store.dispatch(AutoStore.Action.ClearIP)
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
                    CardAuto(auto = it, navController)
                }
            }
        }
    }
}

@Composable
fun CardAuto(auto: PresenterAuto, navController: NavHostController) {
    val modifier = Modifier.fillMaxWidth()
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
                    contentDescription = "person",
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
            if (auto.comment.isNotBlank()) {
                Divider(color = colors.onSecondary, modifier = Modifier.padding(vertical = 4.dp))
                Text(
                    text = stringResource(id = R.string.comment, auto.comment),
                    style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                )
            }
            Row() {
                auto.listIp.forEach { list->
                    list?.let {
                        Text(
                            text = stringResource(id = R.string.reg_number_ip, it.regNumberIP),
                            style = DebitTheme.typography.body16.copy(color = colors.text)
                        )
                    }
                }
            }

        }
    }
}