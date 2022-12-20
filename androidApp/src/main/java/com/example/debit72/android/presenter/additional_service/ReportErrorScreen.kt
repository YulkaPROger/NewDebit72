package  com.example.debit72.android.presenter.additional_service

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.OpenInFull
import androidx.compose.material.icons.rounded.OpenInNew
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.debit72.android.R
import com.example.debit72.android.presenter.theme.DebitTheme
import com.example.debit72.android.widgets.ArrowBackWithSearch
import com.example.debit72.android.widgets.CardFace
import com.example.debit72.android.widgets.FlipCard
import com.example.debit72.android.widgets.RotationAxis
import kotlinx.coroutines.delay
import model.ReportError

@Composable
fun ReportErrorScreen(navController: NavHostController) {
    val store = viewModel<ReportErrorStore>()
    var reportList: List<ReportError>? by remember { mutableStateOf(null) }
    var error: String? by remember {
        mutableStateOf(null)
    }
    var loading: Boolean by remember {
        mutableStateOf(false)
    }
    val state = store.observeState().collectAsState().value
    val lifecycleOwner = LocalLifecycleOwner.current
    var query by remember {
        mutableStateOf("")
    }
    LaunchedEffect(state) {
        if (state is ReportErrorStore.State.Data && query.isEmpty() && state.query.isNotEmpty()) {
            query = state.query
        }
    }

    when (state) {
        is ReportErrorStore.State.Data -> {
            reportList = state.filtredList
            loading = false
            error = null
        }
        ReportErrorStore.State.Loading -> {
            loading = true
            reportList = emptyList()
            error = null
        }
        is ReportErrorStore.State.LoadingError -> {
            error = state.error
            loading = false
            reportList = emptyList()
        }
        null -> {
            store.dispatch(ReportErrorStore.Action.Start)
        }
    }

    LaunchedEffect(query) {
        delay(500)
        store.dispatch(ReportErrorStore.Action.Search(query))
    }
    Column() {
        ArrowBackWithSearch(
            navController,
            query,
            error,
            { query = it },
            reportList?.count(),
        )
        if (loading)
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = DebitTheme.colors.primaryVariant,
                )
            }
        LazyColumn(
            contentPadding = PaddingValues(bottom = 56.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            reportList?.forEach {
                item {
                    ReportErrorCard(it, navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReportErrorCard(reportError: ReportError, navController: NavHostController) {
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
        modifier = Modifier.fillMaxWidth(),
        front = {
            Column(modifier = modifier.padding(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = reportError.rosp,
                        style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                    )
                    Icon(
                        imageVector = Icons.Rounded.OpenInNew, contentDescription = "open ip",
                        tint = DebitTheme.colors.onSecondary,
                        modifier = Modifier.clickable {
                            navController.navigate("fullIP/${reportError.numberIpInside}")
                        }
                    )
                }
                Text(
                    text = stringResource(id = R.string.spi, reportError.spi),
                    style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                )
                Text(
                    text = stringResource(
                        id = R.string.ip,
                        reportError.numberIP
                    ),
                    style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                )
                Text(
                    text = stringResource(
                        id = R.string.production_status,
                        reportError.stateIP
                    ),
                    style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                )
                Text(
                    text = stringResource(
                        id = R.string.number_id,
                        reportError.numberID
                    ),
                    style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                )
                Text(
                    text = stringResource(
                        id = R.string.debtor,
                        reportError.debtor
                    ),
                    style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                )
                Text(
                    text = stringResource(
                        id = R.string.claimant,
                        reportError.claimant
                    ),
                    style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                )
                Text(
                    text = stringResource(
                        id = R.string.date_petition,
                        reportError.date
                    ),
                    style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                )
            }
        },
        back = {
            Column(modifier = modifier.padding(8.dp)) {
                Text(
                    text = stringResource(
                        id = R.string.essence_error,
                        reportError.essenceError
                    ),
                    style = DebitTheme.typography.body16.copy(
                        color = DebitTheme.colors.text
                    )
                )
                Text(
                    text = stringResource(
                        id = R.string.comment_error,
                        reportError.comment
                    ),
                    style = DebitTheme.typography.body16.copy(
                        color = DebitTheme.colors.text
                    )
                )
                Text(
                    text = stringResource(
                        id = R.string.state_error,
                        reportError.state
                    ),
                    style = DebitTheme.typography.body16.copy(
                        color = DebitTheme.colors.text
                    )
                )
            }
        }
    )
}
