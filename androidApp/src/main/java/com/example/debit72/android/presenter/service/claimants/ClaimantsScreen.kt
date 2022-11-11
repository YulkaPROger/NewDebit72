package com.example.debit72.android.presenter.service.claimants

import android.app.Activity
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.IosShare
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.debit72.android.R
import com.example.debit72.android.presenter.theme.DebitTheme
import com.example.debit72.android.presenter.theme.DebitTheme.colors
import com.example.debit72.android.presenter.theme.typography
import com.example.debit72.android.utils.FileUtils
import com.example.debit72.android.utils.Shimmering
import com.example.debit72.android.utils.TypeFile
import com.example.debit72.android.widgets.ArrowBackWithSearch
import com.example.debit72.repository.ClaimantsRepository
import kotlinx.coroutines.launch
import model.Claimant

@Composable
fun ClaimantsScreen(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    var claimantsList: List<Claimant>? by remember { mutableStateOf(null) }
    var filtredList: List<Claimant>? by remember {
        mutableStateOf(null)
    }
    var error: String? by remember {
        mutableStateOf(null)
    }
    LaunchedEffect(true) {
        scope.launch {
            kotlin.runCatching {
                ClaimantsRepository().getClaimantsList()
            }.onSuccess {
                claimantsList = it
                filtredList = claimantsList
            }.onFailure {
                error = it.message
            }
        }
    }
    var query by remember {
        mutableStateOf("")
    }
    LaunchedEffect(query) {
        filtredList = claimantsList?.filter {
            it.name.contains(query, true)
        }
    }
    Shimmering(isVisible = claimantsList == null) {
        Column() {
            ArrowBackWithSearch(
                navController,
                query,
                error,
                { query = it },
                filtredList?.count()
            )
            LazyColumn(contentPadding = PaddingValues(bottom = 56.dp)) {
                filtredList?.forEach {
                    item {
                        ClaimantsCard(it)
                    }
                }
            }
        }
    }
}

@Composable
fun ClaimantsCard(claimant: Claimant) {
    val context = LocalContext.current
    val text = claimant.name + "\n" +
            stringResource(id = R.string.claimant_full_name, claimant.fullName) + "\n" +
            stringResource(id = R.string.claimant_owner, claimant.owner) + "\n" +
            stringResource(id = R.string.claimant_phone, claimant.phone) + "\n" +
            stringResource(id = R.string.claimant_email, claimant.email) + "\n" +
            stringResource(id = R.string.bank_with_params, claimant.bank) + "\n" +
            stringResource(id = R.string.bic, claimant.bic) + "\n" +
            stringResource(id = R.string.inn, claimant.inn) + "\n" +
            stringResource(id = R.string.kpp, claimant.kpp) + "\n" +
            stringResource(id = R.string.rs, claimant.rs) + "\n" +
            stringResource(id = R.string.ks, claimant.ks)


    var expanded by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = colors.cardColor)
            .clickable {
                expanded = !expanded
            },
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = claimant.name, style = typography.bodyNormal18.copy(
                    color = colors.onSecondary
                ),
                overflow = TextOverflow.Ellipsis
            )
            Icon(
                imageVector = Icons.Rounded.IosShare,
                contentDescription = "",
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(24.dp)
                    .clickable {
                        context
                            .let { it1 -> FileUtils.with(it1, TypeFile.PICTURE) }
                            .share(text, claimant.name, context as Activity)
                    },
                tint = colors.onSecondary
            )
        }
        if (expanded)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .animateContentSize()
            ) {
                Divider(color = colors.onSecondary, modifier = Modifier.padding(4.dp))
                Text(
                    text = stringResource(id = R.string.claimant_full_name, claimant.fullName),
                    style = DebitTheme.typography.body16.copy(color = colors.text)
                )
                Text(
                    text = stringResource(id = R.string.claimant_owner, claimant.owner),
                    style = DebitTheme.typography.body16.copy(color = colors.text)
                )
                Text(
                    text = stringResource(id = R.string.claimant_phone, claimant.phone),
                    style = DebitTheme.typography.body16.copy(color = colors.text)
                )
                Text(
                    text = stringResource(id = R.string.claimant_email, claimant.email),
                    style = DebitTheme.typography.body16.copy(color = colors.text)
                )
                Text(
                    text = claimant.address,
                    style = DebitTheme.typography.body16.copy(color = colors.text)
                )
                Divider(color = colors.onSecondary, modifier = Modifier.padding(4.dp))
                Text(
                    text = stringResource(id = R.string.bank_with_params, claimant.bank),
                    style = DebitTheme.typography.body16.copy(color = colors.text)
                )
                Text(
                    text = stringResource(id = R.string.bic, claimant.bic),
                    style = DebitTheme.typography.body16.copy(color = colors.text)
                )
                Text(
                    text = stringResource(id = R.string.inn, claimant.inn),
                    style = DebitTheme.typography.body16.copy(color = colors.text)
                )
                Text(
                    text = stringResource(id = R.string.kpp, claimant.kpp),
                    style = DebitTheme.typography.body16.copy(color = colors.text)
                )
                Text(
                    text = stringResource(id = R.string.rs, claimant.rs),
                    style = DebitTheme.typography.body16.copy(color = colors.text)
                )
                Text(
                    text = stringResource(id = R.string.ks, claimant.ks),
                    style = DebitTheme.typography.body16.copy(color = colors.text)
                )
            }
    }
}

