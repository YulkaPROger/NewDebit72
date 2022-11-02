package com.example.debit72.android.presenter.registry_ip.widgets

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.debit72.android.R
import model.FullIP

@Composable
fun StoriesRowFullIP(ip: FullIP?) {
    LazyRow() {
        items(1) {
            NavigationButtonFullIP(
                name = stringResource(
                    id = R.string.fond,
                    ip?.address ?: "aggregate2"
                )
            )
            NavigationButtonFullIP(
                name = stringResource(R.string.ls, ip?.ls ?: "hjvkjkb"),
            )
            NavigationButtonFullIP(
                name = stringResource(
                    R.string.bank,
                    ip?.bank ?: "hjvkb",
                    ip?.dateSubmissionBank ?: "hjvkb",
                    ip?.dateRevokedBank ?: "hjvkb",
                    ip?.bankStatus ?: "fghjkl;"
                ),
                widthCard = 2.0
            )
            NavigationButtonFullIP(
                name = stringResource(
                    R.string.period,
                    ip?.periodFrom ?: "hjvkb",
                    ip?.periodTo ?: "hjvkb",
                )
            )
            NavigationButtonFullIP(
                name = stringResource(
                    R.string.date_of_explication,
                    ip?.dateOfApplication ?: "hjvkb",
                    ip?.dateExcitation ?: "hjvkb",
                    ip?.dateCancelledIP ?: "dfghjgh"
                ),
                widthCard = 2.0
            )
            NavigationButtonFullIP(
                name = stringResource(
                    R.string.penalties_for_ID,
                    ip?.ZHKU ?: "hjvkb",
                    ip?.duty ?: "hjvkb",
                    ip?.penalties ?: "hjvkb",
                    ip?.yurServices ?: "hjvkb",
                ),
                widthCard = 2.0
            )
        }
    }
}
