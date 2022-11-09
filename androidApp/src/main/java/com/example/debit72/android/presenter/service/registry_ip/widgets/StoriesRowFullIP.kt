package com.example.debit72.android.presenter.service.registry_ip.widgets

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.debit72.android.R
import com.example.debit72.android.presenter.theme.DebitTheme
import model.FullIP

@Composable
fun StoriesRowFullIP(ip: FullIP?) {
    LazyRow() {
        items(1) {
            NavigationButtonFullIP(
                title = stringResource(
                    id = R.string.fond
                ),
                text1 = ip?.address ?: "aggregate2",
                style = DebitTheme.typography.titleMedium20.copy(
                    color = DebitTheme.colors.text,
                    fontWeight = FontWeight.Normal
                )
            )
            NavigationButtonFullIP(
                title = stringResource(R.string.ls),
                text1 = ip?.ls ?: "hjvkjkb",
                alignment = TextAlign.Center,
                style = DebitTheme.typography.titleMedium20.copy(
                    color = DebitTheme.colors.text
                )
            )
            NavigationButtonFullIP(
                title = stringResource(
                    R.string.penalties_for_ID
                ),
                text1 = if (ip?.periodFrom == null) "fgcvj" else "ЖКУ: ${ip.ZHKU}\nПени: ${ip.penalties}\n" +
                        "Пошлина: ${ip.duty}\n" +
                        "Юр.услуги: ${ip.yurServices}",
            )
            NavigationButtonFullIP(
                title = stringResource(
                    R.string.period
                ),
                text1 =
                if (ip?.periodFrom == null) "fgcvj" else "c:  ${ip.periodFrom}\nпо:  ${ip.periodTo}",
                alignment = TextAlign.Center,
                style = DebitTheme.typography.titleMedium20.copy(
                    color = DebitTheme.colors.text,
                    fontWeight = FontWeight.Normal
                )
            )
            NavigationButtonFullIP(
                title = stringResource(
                    R.string.rosp
                ),
                text1 = stringResource(
                    R.string.date_of_explication,
                    ip?.dateOfApplication ?: "hjvkb",
                    ip?.dateExcitation ?: "hjvkb",
                    ip?.dateCancelledIP ?: "dfghjgh"
                ),
            )
            NavigationButtonFullIP(
                title = stringResource(
                    R.string.bank
                ),
                text1 = ip?.bank ?: "vfgbhj",
                text2 = stringResource(
                    id = R.string.bank_status, ip?.dateSubmissionBank ?: "vbn",
                    ip?.dateRevokedBank ?: "CFVGHJ", ip?.bankStatus ?: "vbnm"
                ),
                alignment = TextAlign.Center
            )
        }
    }
}
