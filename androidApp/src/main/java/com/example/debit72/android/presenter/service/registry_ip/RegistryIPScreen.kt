package com.example.debit72.android.presenter.service.registry_ip

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.Cottage
import androidx.compose.material.icons.rounded.LocalPolice
import androidx.compose.material.icons.rounded.SelfImprovement
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.debit72.SpaceXSDK
import com.example.debit72.android.R
import com.example.debit72.android.presenter.navigation.NameRoute
import com.example.debit72.android.presenter.theme.DebitTheme.colors
import com.example.debit72.android.presenter.theme.DebitTheme.typography
import com.example.debit72.android.widgets.ArrowBackWithSearch
import com.example.debit72.android.widgets.DebitTextFieldDense
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.IP

@Composable
fun RegistryIP(navController: NavHostController) {
    val store: RegistryIPStore = viewModel(factory = RegistryIPStoreFactory(LocalContext.current))
    val state = store.observeState().collectAsState().value
    val modifier = Modifier.fillMaxWidth()
    var query by remember {
        mutableStateOf("")
    }

    var list: List<IP>? by remember { mutableStateOf(null) }
    var error: String? by remember {
        mutableStateOf(null)
    }
    when (state) {
        is RegistryIPStore.State.Data -> {
            list = state.ip
            error = ""
        }
        is RegistryIPStore.State.LoadingError -> {
            error = state.error
            list = emptyList()
        }
        else -> {}
    }
    LaunchedEffect(key1 = Unit) {
        when (state) {
            is RegistryIPStore.State.Data -> {
                query = state.query ?: ""
            }
            else -> {}
        }
    }

    LaunchedEffect(query) {
        if (query.length > 3) {
            store.dispatch(RegistryIPStore.Action.Search(query))
        } else store.dispatch(RegistryIPStore.Action.ClearIP)
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
                    CardIP(ip = it, navController)
                }
            }
        }
    }
}

@Composable
fun CardIP(ip: IP, navController: NavHostController) {
    val modifier = Modifier.fillMaxWidth()
    Surface(
        modifier = modifier
            .clip(
                RoundedCornerShape(8.dp)
            )
            .clickable {
                navController.navigate("fullIP/${ip.number}")
            },
        color = colors.cardColor
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
                    tint = colors.primaryVariant
                )
                Text(
                    text = ip.debtor,
                    style = typography.body16.copy(color = colors.text)
                )
            }
            Row(
                modifier = modifier, verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = ip.address,
                    style = typography.body16.copy(color = colors.text)
                )
                Icon(
                    imageVector = Icons.Rounded.Cottage,
                    contentDescription = "person",
                    tint = colors.onSecondary
                )
            }
            Divider(color = colors.onSecondary, modifier = Modifier.padding(vertical = 4.dp))
            Text(
                text = ip.claimant,
                style = typography.body16.copy(color = colors.text)
            )
            Text(
                text = stringResource(id = R.string.id, ip.idNumber),
                style = typography.body16.copy(color = colors.text)
            )
            Text(
                text = stringResource(id = R.string.ip, ip.regNumberIP),
                style = typography.body16.copy(color = colors.text)
            )
            Text(
                text = ip.rosp,
                style = typography.body16.copy(color = colors.text)
            )
            if (ip.spi.isNotBlank())
                Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.LocalPolice,
                        contentDescription = "spi",
                        tint = colors.onSecondary
                    )
                    Text(
                        text = ip.spi,
                        style = typography.body16.copy(color = colors.text)
                    )
                }
            Divider(color = colors.onSecondary, modifier = Modifier.padding(vertical = 4.dp))
            Text(
                text = stringResource(
                    id = R.string.total_amount_debt_with_params,
                    ip.totalAmountDebt
                ),
                style = typography.body16.copy(color = colors.text)
            )
            Text(
                text = stringResource(
                    id = R.string.balance_owed_with_params,
                    ip.balanceOwed
                ),
                style = typography.body16.copy(color = colors.text)
            )
        }
    }
}
