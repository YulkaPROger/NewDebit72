package com.example.debit72.android.presenter.registry_ip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
                        text = it, style = typography.body16.copy(
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
            item {
                RegInformation(ip = ip)
            }
            item {
                IdInformation(ip = ip)
            }
            item {
                DebtorInformation(ip = ip)
            }
            item {
                AutoCard(ip = ip)
            }
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AutoCard(ip: FullIP?) {
    var state by remember {
        mutableStateOf(CardFace.Front)
    }
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        ip?.auto?.forEach {
            item {
                FlipCard(
                    cardFace = state,
                    onClick = {
                        state = it.next
                    },
                    axis = RotationAxis.AxisY,
                    back = {
                        Text(text = "Front", Modifier
                            .fillMaxSize()
                            .background(Color.Red))
                    },
                    front = {
                        Column(modifier = Modifier) {
                            Text(
                                text = it.auto,
                                style = typography.body16.copy(
                                    color = colors.text
                                ),
                                modifier = Modifier.shimmering()
                            )
                            Text(
                                text = stringResource(id = R.string.model, it.modelTS),
                                style = typography.body16.copy(
                                    color = colors.text
                                ),
                                modifier = Modifier.shimmering()
                            )
                            Text(
                                text = stringResource(id = R.string.gosnomer, it.gosNumber),
                                style = typography.body16.copy(
                                    color = colors.text
                                ),
                                modifier = Modifier.shimmering()
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun DebtorInformation(ip: FullIP?) {
    val aggregate1 = "рандом 1"
    val aggregate2 = "лорем ипсум"
    val aggregate3 = "чушь"
    val aggregate4 = "какое-то длинное предложение"
    val aggregate5 = "заполнитель всея руси"
    Surface(
        color = colors.cardColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp)),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Rounded.Person, contentDescription = "",
                    tint = colors.onSurface,
                )
                Icon(
                    imageVector = if (ip?.died == true) Icons.Rounded.SettingsAccessibility else Icons.Rounded.Check,
                    contentDescription = "",
                    tint = if (ip?.died == true) colors.error else colors.onSurface,
                )
            }
            Column(
                modifier = Modifier
                    .weight(7f)
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = stringResource(
                        id = R.string.debtor,
                        ip?.debtor ?: aggregate1,
                        ip?.dateID ?: aggregate2
                    ),
                    style = typography.body16.copy(
                        color = colors.text
                    ),
                    modifier = Modifier.shimmering()
                )
                Text(
                    text = stringResource(
                        id = R.string.dr,
                        ip?.debtorBirthday ?: aggregate3
                    ),
                    style = typography.body16.copy(
                        color = colors.text
                    ),
                    modifier = Modifier.shimmering()
                )
                Text(
                    text = stringResource(
                        id = R.string.mr,
                        ip?.placeOfBirth ?: aggregate3
                    ),
                    style = typography.body16.copy(
                        color = colors.text
                    ),
                    modifier = Modifier.shimmering()
                )
                Text(
                    text = stringResource(
                        id = R.string.debtor_address,
                        ip?.debtorAddress ?: aggregate3
                    ),
                    style = typography.body16.copy(
                        color = colors.text
                    ),
                    modifier = Modifier.shimmering()
                )
                Text(
                    text = stringResource(
                        id = R.string.debtor_fact_address,
                        ip?.debtorAddressFact ?: aggregate3
                    ),
                    style = typography.body16.copy(
                        color = colors.text
                    ),
                    modifier = Modifier.shimmering()
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.pensioner),
                    style = typography.body16.copy(
                        color = colors.text
                    ),
                )
                Icon(
                    imageVector = if (ip?.pensioner == true) Icons.Rounded.CheckCircle else Icons.Rounded.Close,
                    contentDescription = "",
                    tint = if (ip?.pensioner == true) colors.onSurface else colors.error,
                )
            }
        }
    }
}

@Composable
fun IdInformation(ip: FullIP?) {
    val aggregate1 = "рандом 1"
    val aggregate2 = "лорем ипсум"
    val aggregate3 = "чушь"
    val aggregate4 = "какое-то длинное предложение"
    val aggregate5 = "заполнитель всея руси"
    Surface(
        color = colors.cardColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp)),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Rounded.Info, contentDescription = "",
                    tint = colors.onSurface,
                )
            }
            Column(
                modifier = Modifier
                    .weight(7f)
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = stringResource(
                        id = R.string.id_number_and_date,
                        ip?.numberID ?: aggregate1,
                        ip?.dateID ?: aggregate2
                    ),
                    style = typography.body16.copy(
                        color = colors.text
                    ),
                    modifier = Modifier.shimmering()
                )
                Text(
                    text = stringResource(
                        id = R.string.type_id,
                        ip?.typeID ?: aggregate3
                    ),
                    style = typography.body16.copy(
                        color = colors.text
                    ),
                    modifier = Modifier.shimmering()
                )
                Text(
                    text = ip?.court ?: aggregate4,
                    style = typography.body16.copy(
                        color = colors.text
                    ),
                    modifier = Modifier.shimmering()
                )
                Text(
                    text = ip?.caseNumber ?: aggregate5,
                    style = typography.body16.copy(
                        color = colors.text
                    ),
                    modifier = Modifier.shimmering()
                )
            }
        }
    }
}

@Composable
fun RegInformation(ip: FullIP?) {
    val aggregate1 = "рандом 1"
    val aggregate2 = "лорем ипсум"
    val aggregate3 = "чушь"
    val aggregate4 = "какое-то длинное предложение"
    val aggregate5 = "заполнитель всея руси"
    Surface(
        color = colors.cardColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp)),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Rounded.Info, contentDescription = "",
                    tint = colors.onSurface,
                )
            }
            Column(
                modifier = Modifier
                    .weight(7f)
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = stringResource(
                        id = R.string.reg_number_ip,
                        ip?.registryNumberIP ?: aggregate1
                    ),
                    style = typography.body16.copy(
                        color = colors.text
                    ),
                    modifier = Modifier.shimmering()
                )
                Text(
                    text = stringResource(
                        id = R.string.production_status,
                        ip?.productionStatus ?: aggregate2
                    ),
                    style = typography.body16.copy(
                        color = colors.text
                    ),
                    modifier = Modifier.shimmering()
                )
                Text(
                    text = ip?.fssp ?: aggregate3,
                    style = typography.body16.copy(
                        color = colors.text
                    ),
                    modifier = Modifier.shimmering()
                )
                Text(
                    text = stringResource(id = R.string.spi, ip?.spi ?: aggregate4),
                    style = typography.body16.copy(
                        color = colors.text
                    ),
                    modifier = Modifier.shimmering()
                )
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
                style = typography.body16.copy(
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
                style = typography.body16.copy(
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
            color = colors.cardColor,
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
                    style = typography.body16.copy(
                        color = colors.text
                    ),
                    modifier = Modifier.shimmering()
                )

            }
        }
        Surface(
            color = colors.cardColor,
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
                    style = typography.body16.copy(
                        color = colors.text
                    ),
                    modifier = Modifier.shimmering()
                )
                Text(
                    text = stringResource(
                        id = R.string.total_debt_with_param,
                        ip?.totalAmountDebt ?: aggregate4
                    ),
                    style = typography.body16.copy(
                        color = colors.text
                    ),
                    modifier = Modifier.shimmering()
                )
                Text(
                    text = stringResource(
                        id = R.string.balance_owed_fssp_with_param,
                        ip?.balanceFSSP ?: aggregate5
                    ),
                    style = typography.body16.copy(
                        color = colors.text
                    ),
                    modifier = Modifier.shimmering()
                )
            }

        }
    }
}

