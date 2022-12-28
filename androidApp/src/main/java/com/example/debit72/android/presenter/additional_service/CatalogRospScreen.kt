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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.debit72.android.presenter.theme.DebitTheme
import com.example.debit72.android.widgets.ArrowBackWithSearch
import com.example.debit72.repository.AdditionalSerciceRepository
import kotlinx.coroutines.launch
import model.CatalogRosp

@Composable
fun CatalogRospScreen(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    var requestList: List<CatalogRosp>? by remember { mutableStateOf(null) }
    var filteredList: List<CatalogRosp>? by remember {
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
                AdditionalSerciceRepository().catalogRospList()
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
            it.name.contains(query, true)
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
                    CatalogRospCard(it)
                }
            }
        }
    }
}

@Composable
fun CatalogRospCard(rosp: CatalogRosp) {
    val modifier = Modifier.fillMaxWidth()
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) {}
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
            Text(
                text = rosp.name,
                style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
            )
            Text(
                text = rosp.owner,
                style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
            )
            Text(
                text = rosp.eMail,
                style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text),
                modifier = Modifier.clickable {
                    clipboardManager.setText(AnnotatedString((rosp.eMail)))
                }
            )
            val listPhone = rosp.phone.split(",")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listPhone.forEach {
                    Text(
                        text = it.trimEnd().trimStart(),
                        style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text),
                        modifier = Modifier.clickable {
                            when (PackageManager.PERMISSION_GRANTED) {
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CALL_PHONE
                                ) -> {
                                    makePhoneCall(context, it)
                                }
                                else -> {
                                    launcher.launch(Manifest.permission.CALL_PHONE)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

private fun makePhoneCall(context: Context, number: String) {
    val intent = Intent(Intent.ACTION_CALL)
    intent.data = Uri.parse("tel:$number")
    try {
        ContextCompat.startActivity(context, intent, null)
    } catch (e: Exception) {
    }
}