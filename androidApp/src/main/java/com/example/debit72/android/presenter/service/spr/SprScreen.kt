package com.example.debit72.android.presenter.service.spr

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
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
import com.example.debit72.android.utils.Shimmering
import com.example.debit72.android.utils.shimmering
import com.example.debit72.android.widgets.ArrowBackWithSearch
import com.example.debit72.android.widgets.CardFace
import com.example.debit72.android.widgets.FlipCard
import com.example.debit72.android.widgets.RotationAxis
import com.example.debit72.repository.SprRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.FullSpr
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardSpr(spr: Spr, navController: NavHostController) {
    val modifier = Modifier.fillMaxWidth()
    var state by remember {
        mutableStateOf(CardFace.Front)
    }
    FlipCard(
        cardFace = state,
        onClick = {
            state = it.next
        },
        axis = RotationAxis.AxisY,
        widthCard = 1.0,
        back = {
            BackSpr(spr.number)
        },
        front = {
            Surface(
                modifier = modifier
                    .clip(
                        RoundedCornerShape(8.dp)
                    ),
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
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = modifier
                            ) {
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
    )
}

@Composable
fun BackSpr(number: String) {
    var spr: FullSpr? by remember {
        mutableStateOf(null)
    }
    var error: String? by remember {
        mutableStateOf(null)
    }
    LaunchedEffect(1) {
        withContext(Dispatchers.IO) {
            kotlin.runCatching {
                SprRepository().getSprForLink(number)
            }.onSuccess {
                spr = it
            }.onFailure {
                error = it.message
            }
        }
    }
    Shimmering(isVisible = spr == null) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(8.dp)
                ),
            color = colors.cardColor
        ) {
            Column() {
                Row() {
                    Text(
                        text = stringResource(id = R.string.periodFrom, spr?.periodS ?: "dfghjk"),
                        style = typography.body16.copy(
                            color = colors.text
                        ),
                        modifier = Modifier.shimmering()
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = stringResource(id = R.string.periodTo, spr?.periodPo ?: "fghjkkjhgffdfy"),
                        style = typography.body16.copy(
                            color = colors.text
                        ),
                        modifier = Modifier.shimmering()
                    )
                }
                Row() {
                    Text(
                        text = stringResource(id = R.string.number_id, spr?.numberId ?: "gh kjnk"),
                        style = typography.body16.copy(
                            color = colors.text
                        ),
                        modifier = Modifier.shimmering()
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = stringResource(id = R.string.date_id, spr?.date ?: "ghjk"),
                        style = typography.body16.copy(
                            color = colors.text
                        ),
                        modifier = Modifier.shimmering()
                    )
                }
                Text(
                    text = stringResource(id = R.string.number_case, spr?.numberCase ?: "hkdkdkd"),
                    style = typography.body16.copy(
                        color = colors.text
                    ),
                    modifier = Modifier.shimmering()
                )
                Text(
                    text = stringResource(id = R.string.spr_sum_of_debt, spr?.debt ?: "ghj"),
                    style = typography.body16.copy(
                        color = colors.text
                    ),
                    modifier = Modifier.shimmering()
                )
                Text(
                    text = stringResource(
                        id = R.string.spr_sum_of_penalties,
                        spr?.penalties ?: "hjk"
                    ),
                    style = typography.body16.copy(
                        color = colors.text
                    ),
                    modifier = Modifier.shimmering()
                )
                Text(
                    text = stringResource(
                        id = R.string.spr_sum_of_duty,
                        spr?.duty ?: "hjk"
                    ),
                    style = typography.body16.copy(
                        color = colors.text
                    ),
                    modifier = Modifier.shimmering()
                )
            }
        }
    }
}
