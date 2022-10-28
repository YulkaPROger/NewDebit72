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
                painterResource = R.drawable.background_abstraction,
                name = stringResource(R.string.ls, ip?.ls ?: "hjvkjkb"),
            )
            NavigationButtonFullIP(
                painterResource = R.drawable.background_abstraction,
                name = stringResource(
                    R.string.bank,
                    ip?.bank ?: "hjvkb",
                    ip?.dateRevokedBank ?: "hjvkb",
                    ip?.dateSubmissionBank ?: "hjvkb",
                    ip?.bankStatus ?: "fghjkl;"
                )
            )
            NavigationButtonFullIP(
                painterResource = R.drawable.background_abstraction,
                name = stringResource(
                    R.string.period,
                    ip?.periodFrom ?: "hjvkb",
                    ip?.periodTo ?: "hjvkb",
                )
            )
            NavigationButtonFullIP(
                painterResource = R.drawable.background_abstraction,
                name = stringResource(
                    R.string.date_of_explication,
                    ip?.dateOfApplication ?: "hjvkb",
                    ip?.dateExcitation ?: "hjvkb",
                    ip?.dateCancelledIP ?: "dfghjgh"
                )
            )
            NavigationButtonFullIP(
                painterResource = R.drawable.background_abstraction,
                name = stringResource(
                    R.string.penalties_for_ID,
                    ip?.ZHKU ?: "hjvkb",
                    ip?.duty ?: "hjvkb",
                )
            )
            NavigationButtonFullIP(
                painterResource = R.drawable.background_abstraction,
                name = stringResource(
                    R.string.penalties_for_ID_part_2,
                    ip?.penalties ?: "hjvkb",
                    ip?.yurServices ?: "hjvkb",
                )
            )
        }
    }
}
//            NavigationButtonFullIP(
//                painterResource = R.drawable.background_abstraction,
//                name = ip?.ls ?: "hjvkjkb",
//            )
//            NavigationButtonFullIP(
//                painterResource = R.drawable.background_abstraction,
//                name = ip?.ls ?: "hjvkjkb",
//            )
//            NavigationButtonFullIP(
//                painterResource = R.drawable.background_abstraction,
//                name = ip?.ls ?: "hjvkjkb",
//            )
//            NavigationButtonFullIP(
//                painterResource = R.drawable.background_abstraction,
//                name = ip?.ls ?: "hjvkjkb",
//            )
//            NavigationButtonFullIP(
//                painterResource = R.drawable.background_abstraction,
//                name = ip?.ls ?: "hjvkjkb",
//            )
//            NavigationButtonFullIP(
//                painterResource = R.drawable.background_abstraction,
//                name = ip?.ls ?: "hjvkjkb",
//            )
//            NavigationButtonFullIP(
//                painterResource = R.drawable.background_abstraction,
//                name = ip?.ls ?: "hjvkjkb",
//            )
//                }
//        }
//    }
