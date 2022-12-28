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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.debit72.android.presenter.theme.DebitTheme
import com.example.debit72.android.widgets.ArrowBackWithSearch
import com.example.debit72.repository.AdditionalSerciceRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.Spi

@Composable
fun CatalogSpiScreen(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    var requestList: List<Spi>? by remember { mutableStateOf(null) }
    var error: String? by remember {
        mutableStateOf(null)
    }

    var query by remember {
        mutableStateOf("")
    }
    LaunchedEffect(query) {
        delay(700)
        scope.launch {
            kotlin.runCatching {
                AdditionalSerciceRepository().spiList(query)
            }.onSuccess {
                requestList = it
            }.onFailure {
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
            requestList?.forEach {
                item {
                    CatalogSpiCard(it)
                }
            }
        }
    }
}

@Composable
fun CatalogSpiCard(spi: Spi) {
    val modifier = Modifier.fillMaxWidth()
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) {}
    Surface(
        modifier = modifier
            .clip(
                RoundedCornerShape(8.dp)
            ),
        color = DebitTheme.colors.cardColor
    ) {
        Column(modifier = modifier.padding(8.dp)) {
            Text(
                text = spi.name,
                style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
            )
            if (spi.rosp.isNotEmpty())
                Text(
                    text = spi.rosp,
                    style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
                )
            if (spi.phone.isNotEmpty())
                Text(
                    text = spi.phone,
                    style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text),
                    modifier = Modifier.clickable {
                        when (PackageManager.PERMISSION_GRANTED) {
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CALL_PHONE
                            ) -> {
                                makePhoneCall(context, spi.phone)
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

private fun makePhoneCall(context: Context, number: String) {
    val intent = Intent(Intent.ACTION_CALL)
    intent.data = Uri.parse("tel:$number")
    try {
        ContextCompat.startActivity(context, intent, null)
    } catch (e: Exception) {
    }
}