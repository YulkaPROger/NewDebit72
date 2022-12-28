package com.example.debit72.android.presenter.additional_service

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.debit72.android.R
import com.example.debit72.android.presenter.theme.DebitTheme
import com.example.debit72.android.widgets.ArrowBackWithSearch
import com.example.debit72.android.widgets.CardFace
import com.example.debit72.android.widgets.FlipCard
import com.example.debit72.android.widgets.RotationAxis
import com.example.debit72.repository.AdditionalSerciceRepository
import kotlinx.coroutines.launch
import model.Court

@Composable
fun CatalogCourtScreen(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    var requestList: List<Court>? by remember { mutableStateOf(null) }

    var error: String? by remember {
        mutableStateOf(null)
    }

    var query by remember {
        mutableStateOf("")
    }
    var filteredList: List<Court>? = requestList?.filter {
        it.name.contains(query, true) ||
                it.code.contains(query, true) ||
                it.address.contains(query, true)
    }

    LaunchedEffect(Unit) {
        scope.launch {
            kotlin.runCatching {
                AdditionalSerciceRepository().courts()
            }.onSuccess {
                requestList = it
                filteredList = it
            }.onFailure {
                requestList = emptyList()
                filteredList = emptyList()
                error = it.message
            }
        }
    }

    Column {
        ArrowBackWithSearch(
            navController,
            query,
            error,
            { query = it },
            requestList?.count()
        )
        LazyColumn(
            contentPadding = PaddingValues(bottom = 56.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            filteredList?.forEach {
                item {
                    CatalogCourtCard(it)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CatalogCourtCard(court: Court) {
    val modifier = Modifier.fillMaxWidth()
    val context = LocalContext.current
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) {}
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
                    text = stringResource(id = R.string.court, court.code),
                    style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                )
                Text(
                    text = stringResource(id = R.string.address, court.address),
                    style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                )
                Text(
                    text = stringResource(
                        id = R.string.short_phone,
                        court.phone
                    ),
                    style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text),
                    modifier = Modifier.clickable {
                        clipboardManager.setText(AnnotatedString(court.phone))
                        //TODO передумает поди на звонки
//                        when (PackageManager.PERMISSION_GRANTED) {
//                            ContextCompat.checkSelfPermission(
//                                context,
//                                Manifest.permission.CALL_PHONE
//                            ) -> {
//                                makePhoneCall(context, court.phone)
//                            }
//                            else -> {
//                                launcher.launch(Manifest.permission.CALL_PHONE)
//                            }
//                        }
                    }
                )
                Text(
                    text = stringResource(
                        id = R.string.email,
                        court.email
                    ),
                    style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text),
                    modifier = Modifier.clickable {
                        clipboardManager.setText(AnnotatedString(court.email))
                    }
                )
            }
        },
        back = {
            Column(modifier = modifier.padding(8.dp)) {
                Text(
                    text = stringResource(
                        id = R.string.court,
                        court.name
                    ),
                    style = DebitTheme.typography.body16.copy(
                        color = DebitTheme.colors.text
                    )
                )
                Text(
                    text = stringResource(
                        id = R.string.referee,
                        court.referee
                    ),
                    style = DebitTheme.typography.body16.copy(
                        color = DebitTheme.colors.text
                    )
                )
            }
        }
    )
}

private fun makePhoneCall(context: Context, number: String) {
    val intent = Intent(Intent.ACTION_CALL)
    intent.data = Uri.parse("tel:$number")
    try {
        ContextCompat.startActivity(context, intent, null)
    } catch (e: Exception) {
    }
}