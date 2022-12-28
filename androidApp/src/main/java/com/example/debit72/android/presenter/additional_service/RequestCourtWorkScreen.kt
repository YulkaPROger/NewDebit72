package com.example.debit72.android.presenter.additional_service

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.debit72.android.R
import com.example.debit72.android.presenter.theme.DebitTheme
import com.example.debit72.android.widgets.ArrowBackWithSearch
import com.example.debit72.android.widgets.CardFace
import com.example.debit72.android.widgets.FlipCard
import com.example.debit72.android.widgets.RotationAxis
import com.example.debit72.repository.AdditionalSerciceRepository
import kotlinx.coroutines.launch
import model.RequestCourtWork

@Composable
fun RequestCourtWorkScreen(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    var requestList: List<RequestCourtWork>? by remember { mutableStateOf(null) }
    var filteredList: List<RequestCourtWork>? by remember {
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
                AdditionalSerciceRepository().requestCourtWorkList()
            }.onSuccess {
                requestList = it
                filteredList = it
            }.onFailure {
                error = it.message
            }
        }
    }

    var query by remember {
        mutableStateOf("")
    }
    LaunchedEffect(query, dateFilter) {
        filteredList = requestList?.filter {
            it.court.contains(query, true) ||
                    it.requestTo.contains(query, true)
        }
    }
    Column {
        ArrowBackWithSearch(
            navController,
            query,
            error,
            { query = it },
            filteredList?.count()
        )
        LazyColumn(
            contentPadding = PaddingValues(bottom = 56.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            filteredList?.forEach {
                item {
                    RequestCourtWorkCard(it)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RequestCourtWorkCard(courtWork: RequestCourtWork) {
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
                Text(
                    text = stringResource(id = R.string.court, courtWork.court),
                    style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                )
                Text(
                    text = stringResource(id = R.string.request_to, courtWork.requestTo),
                    style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                )
                Text(
                    text = stringResource(id = R.string.date_request, courtWork.dateRequest),
                    style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                )
                Text(
                    text = stringResource(id = R.string.date_response, courtWork.dateResponse),
                    style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                )
                Text(
                    text = stringResource(id = R.string.claimant, courtWork.claimant),
                    style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                )
            }
        },
        back = {
            Column(modifier = modifier.padding(8.dp)) {
                Text(
                    text = stringResource(id = R.string.subject_request, courtWork.subject),
                    style = DebitTheme.typography.body16.copy(
                        color = DebitTheme.colors.text
                    )
                )
            }
        }
    )
}