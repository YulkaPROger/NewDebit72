package com.example.debit72.android.presenter.service.spr

import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.example.debit72.android.presenter.theme.DebitTheme.colors
import com.example.debit72.android.presenter.theme.typography
import com.example.debit72.repository.SprRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.FullSpr

@Composable
fun FullSprScreen(number: String?) {

    var spr: FullSpr? by remember {
        mutableStateOf(null)
    }
    var error: String? by remember {
        mutableStateOf(null)
    }
    LaunchedEffect(1) {
        if (number != null)
            withContext(Dispatchers.IO) {
                kotlin.runCatching {
                    SprRepository().getSprForLink(number)
                }.onSuccess {
                    spr = it
                }.onFailure {
                    error = it.message
                }
            }
        else error = "Не удалось получить номер ИП"
    }
    if (spr != null) {
        Text(
            text = spr.toString(),
            style = typography.titleMedium20.copy(
                color = colors.text
            )
        )
    }
}