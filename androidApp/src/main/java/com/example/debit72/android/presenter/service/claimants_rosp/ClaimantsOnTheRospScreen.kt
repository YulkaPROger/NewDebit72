package com.example.debit72.android.presenter.service.claimants_rosp

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.IosShare
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.debit72.android.presenter.theme.DebitTheme
import com.example.debit72.android.presenter.theme.typography
import com.example.debit72.android.utils.FileUtils
import com.example.debit72.android.utils.Shimmering
import com.example.debit72.android.utils.TypeFile
import com.example.debit72.android.widgets.ArrowBackWithSearch
import com.example.debit72.repository.ClaimantsRepository
import kotlinx.coroutines.launch
import model.ClaimantsOnTheRosp

@Composable
fun ClaimantsOnTheRospScreen(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    var claimantsList: List<ClaimantsOnTheRosp>? by remember { mutableStateOf(null) }
    var filtredList: List<ClaimantsOnTheRosp>? by remember {
        mutableStateOf(null)
    }
    var error: String? by remember {
        mutableStateOf(null)
    }
    LaunchedEffect(true) {
        scope.launch {
            kotlin.runCatching {
                ClaimantsRepository().getClaimantsOnRospList()
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
            it.rosp.contains(query, true)
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
                        ClaimantsOnTheRospCard(it)
                    }
                }
            }
        }
    }
}

@Composable
fun ClaimantsOnTheRospCard(claimant: ClaimantsOnTheRosp) {
    val context = LocalContext.current
    var expanded by remember {
        mutableStateOf(false)
    }
    val text: String = getStringForRosp(claimant)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = DebitTheme.colors.cardColor)
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
                text = claimant.rosp, style = typography.bodyNormal18.copy(
                    color = DebitTheme.colors.onSecondary
                ),
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.width((LocalConfiguration.current.screenWidthDp * 0.7).dp)
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
                            .share(text, claimant.rosp, context as Activity)
                    },
                tint = DebitTheme.colors.onSecondary
            )
        }
        if (expanded) {
            Column(
                modifier = Modifier
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                claimant.claimant.forEach {
                    Text(
                        text = it.claimant + ", " + it.inn + ", кол-во дел: " + it.countCase,
                        style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                    )
                }
            }
        }
    }
}

fun getStringForRosp(claimant: ClaimantsOnTheRosp): String {
    var text = ""
    claimant.claimant.forEach {
        text = text + "Взыскатель: " + it.claimant + "\n" + "ИНН: " + it.inn + "\n" + "Кол-во дел: " + it.countCase + "\n"
    }
    return text
}
