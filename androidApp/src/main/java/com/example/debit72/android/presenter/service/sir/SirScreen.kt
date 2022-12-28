package com.example.debit72.android.presenter.service.sir

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.debit72.android.R
import com.example.debit72.android.presenter.theme.DebitTheme
import com.example.debit72.android.utils.Shimmering
import com.example.debit72.android.widgets.ArrowBackWithSearch
import com.example.debit72.android.widgets.CardFace
import com.example.debit72.android.widgets.FlipCard
import com.example.debit72.android.widgets.RotationAxis
import com.example.debit72.repository.SirRepository
import kotlinx.coroutines.launch
import model.Sir
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun SirScreen(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    var sirList: List<Sir>? by remember { mutableStateOf(null) }
    var filtredList: List<Sir>? by remember {
        mutableStateOf(null)
    }
    var error: String? by remember {
        mutableStateOf(null)
    }
    var dateFilter: Boolean by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(true) {
        scope.launch {
            kotlin.runCatching {
                SirRepository().getSirList()
            }.onSuccess {
                sirList = it
                filtredList = it
            }.onFailure {
                error = it.message
            }
        }
    }

    var query by remember {
        mutableStateOf("")
    }
    LaunchedEffect(query, dateFilter) {
        filtredList = sirList?.filter {
            if (dateFilter)
                it.dateConversations.isNotEmpty()
            else true
        }?.filter {
            it.claimant.contains(query, true) ||
                    it.naKogo.contains(query, true) ||
                    it.court.contains(query, true) ||
                    it.dateConversations.contains(query, true) ||
                    it.number.contains(query, true)
        }
    }
    Shimmering(isVisible = sirList == null) {
        Column() {
            ArrowBackWithSearch(
                navController,
                query,
                error,
                { query = it },
                filtredList?.count(),
                additionalFilter = {
                    Icon(
                        Icons.Outlined.EventAvailable,
                        contentDescription = "",
                        tint = if (dateFilter) DebitTheme.colors.primary else DebitTheme.colors.gray700,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable {
                                dateFilter = !dateFilter
                            }
                    )
                }
            )
            LazyColumn(
                contentPadding = PaddingValues(bottom = 56.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                filtredList?.sortedByDescending {
                    var date = it.dateConversations
                    if (date == "") {
                        date = "01.01.1900"
                    }
                    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                    val dateFormatted = LocalDate.parse(date, formatter)
                    dateFormatted
                }?.forEach {
                    item {
                        SirCard(it)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SirCard(sir: Sir) {
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
                    modifier = modifier,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = sir.claimant,
                        style = DebitTheme.typography.bodyNormal18.copy(
                            color = DebitTheme.colors.onSecondary
                        )
                    )
                }
                Row(
                    modifier = modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(id = R.string.court, sir.court),
                            style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                        )
                        Text(
                            text = stringResource(id = R.string.referee, sir.fioReferee),
                            style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                        )
                        Text(
                            text = stringResource(id = R.string.debtors, sir.naKogo),
                            style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                        )
                        Text(
                            text = stringResource(id = R.string.date_meeting, sir.dateMeetings),
                            style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                        )

                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(id = R.string.number_case_with_n, sir.number),
                            style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                        )
                        Text(
                            text = stringResource(
                                id = R.string.date_conversation,
                                sir.dateConversations
                            ),
                            style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                        )
                    }
                }
                Row(
                    modifier = modifier,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = sir.address,
                        style = DebitTheme.typography.bodyNormal18.copy(
                            color = DebitTheme.colors.text
                        )
                    )
                }

            }
        },
        back = {
            Column(modifier = modifier.padding(8.dp)) {
                Row() {
                    Text(
                        text = stringResource(id = R.string.periodTo, sir.periodTo),
                        style = DebitTheme.typography.bodyNormal18.copy(
                            color = DebitTheme.colors.text
                        )
                    )
                    Text(
                        text = stringResource(id = R.string.periodFrom, sir.periodFrom),
                        style = DebitTheme.typography.bodyNormal18.copy(
                            color = DebitTheme.colors.text
                        )
                    )
                }
                Text(
                    text = stringResource(id = R.string.spr_sum_of_debt, sir.totalDebt),
                    style = DebitTheme.typography.bodyNormal18.copy(
                        color = DebitTheme.colors.text
                    )
                )
                Text(
                    text = stringResource(id = R.string.spr_sum_of_penalties, sir.penalties),
                    style = DebitTheme.typography.bodyNormal18.copy(
                        color = DebitTheme.colors.text
                    )
                )
                Text(
                    text = stringResource(id = R.string.spr_sum_of_duty, sir.duty),
                    style = DebitTheme.typography.bodyNormal18.copy(
                        color = DebitTheme.colors.text
                    )
                )
                Text(
                    text = stringResource(
                        id = R.string.amount_of_legal_services_with_params,
                        sir.legalServices
                    ),
                    style = DebitTheme.typography.bodyNormal18.copy(
                        color = DebitTheme.colors.text
                    )
                )
            }
        }
    )
}
