package com.example.debit72.android.presenter.registry_ip

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.HMobiledata
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.debit72.android.R
import com.example.debit72.android.presenter.Shimmering
import com.example.debit72.android.presenter.registry_ip.widgets.StoriesRowFullIP
import com.example.debit72.android.presenter.shimmering
import com.example.debit72.android.presenter.theme.DebitTheme.colors
import com.example.debit72.android.presenter.theme.DebitTheme.typography
import com.example.debit72.repository.InfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.FullIP


@Composable
fun FullIPScreen(navController: NavHostController, number: String?) {
    var ip: FullIP? by remember {
        mutableStateOf(null)
    }
    var error: String? by remember {
        mutableStateOf(null)
    }
    LaunchedEffect(1) {
        if (number != null)
            withContext(Dispatchers.IO) {
                kotlin.runCatching {
                    InfoRepository().getIp(number)
                }.onSuccess {
                    ip = it
                }.onFailure {
                    error = it.message
                }
            }
        else error = "Не удалось получить номер ИП"
    }
    Shimmering(isVisible = ip == null) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item {
                error?.let {
                    Text(
                        text = it, style = typography.body14.copy(
                            color = colors.error
                        )
                    )
                }
            }
            item {
                HeaderIP(ip = ip)
            }
            item {
                StoriesRowFullIP(ip = ip)
            }
        }
    }

}

@Composable
fun HeaderIP(ip: FullIP?) {
    val aggregate1 = "рандом 1"
    val aggregate2 = "лорем ипсум"
    val aggregate3 = "чушь"
    val aggregate4 = "какое-то длинное предложение"
    val aggregate5 = "заполнитель всея руси"
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.HMobiledata,
                contentDescription = "",
                tint = colors.onSurface
            )
            Text(
                text = stringResource(id = R.string.numberExcel, ip?.numberExcel ?: aggregate1),
                style = typography.body14.copy(
                    color = colors.text
                ),
                modifier = Modifier.shimmering()
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Rounded.Close, contentDescription = "",
                tint = if (ip?.canceled == true) colors.onSecondary else colors.error
            )
            Text(
                text = stringResource(
                    id = R.string.cancelled,
                ),
                style = typography.body14.copy(
                    color = colors.text
                ),
            )
        }
    }
    Row(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Surface(
            color = colors.gray900,
            modifier = Modifier
                .weight(2f)
                .clip(RoundedCornerShape(8.dp)),
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = stringResource(
                        id = R.string.fond,
                        ip?.address ?: aggregate2
                    ),
                    style = typography.body14.copy(
                        color = colors.text
                    ),
                    modifier = Modifier.shimmering()
                )

            }
        }
        Surface(
            color = colors.gray900,
            modifier = Modifier
                .weight(3f)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = stringResource(
                        id = R.string.balance_owed_with_param,
                        ip?.balanceOwed ?: aggregate3
                    ),
                    style = typography.body14.copy(
                        color = colors.text
                    ),
                    modifier = Modifier.shimmering()
                )
                Text(
                    text = stringResource(
                        id = R.string.total_debt_with_param,
                        ip?.totalAmountDebt ?: aggregate4
                    ),
                    style = typography.body14.copy(
                        color = colors.text
                    ),
                    modifier = Modifier.shimmering()
                )
                Text(
                    text = stringResource(
                        id = R.string.balance_owed_fssp_with_param,
                        ip?.balanceFSSP ?: aggregate5
                    ),
                    style = typography.body14.copy(
                        color = colors.text
                    ),
                    modifier = Modifier.shimmering()
                )
            }

        }
    }
}