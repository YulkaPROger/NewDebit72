package com.example.debit72.android.presenter.registry_ip

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Color
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.debit72.android.FileUtils
import com.example.debit72.android.R
import com.example.debit72.android.TypeFile
import com.example.debit72.android.presenter.Shimmering
import com.example.debit72.android.presenter.registry_ip.widgets.StoriesRowFullIP
import com.example.debit72.android.presenter.shimmering
import com.example.debit72.android.presenter.theme.DebitTheme.colors
import com.example.debit72.android.presenter.theme.DebitTheme.typography
import com.example.debit72.repository.InfoRepository
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.FullIP
import kotlin.math.roundToInt


@Composable
fun FullIPScreen(number: String?) {
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
        LazyColumn(
            modifier = Modifier.padding(vertical = 16.dp),
            contentPadding = PaddingValues(bottom = 50.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
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
            item {
                CardCard(ip = ip)
            }
            item {
                PropertyCard(ip = ip)
            }
            item {
                EmployerCard(ip = ip)
            }
            item {
                PropertyYurCard(ip = ip)
            }
            item {
                ip?.qr?.let { QR(vCardText = it) }
            }
        }
    }

}

@Composable
fun PropertyYurCard(ip: FullIP?) {
    val width = LocalConfiguration.current.screenWidthDp
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ip?.propertyYur?.forEach {
            item {
                Row(
                    modifier = Modifier
                        .width((width * 0.75).dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = colors.cardColor),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CreditCard,
                        contentDescription = "",
                        tint = colors.onSecondary,
                        modifier = Modifier.weight(1f)
                    )
                    Column(
                        modifier = Modifier.weight(8f)
                    ) {
                        Text(
                            text = stringResource(id = R.string.naming, it.propertyYur),
                            style = typography.body16.copy(
                                color = colors.text
                            ),
                            modifier = Modifier.shimmering()
                        )
                        Text(
                            text = stringResource(id = R.string.address, it.address),
                            style = typography.body14.copy(
                                color = colors.text
                            ),
                            modifier = Modifier.shimmering()
                        )
                        Text(
                            text = stringResource(id = R.string.inn, it.inn),
                            style = typography.body14.copy(
                                color = colors.text
                            ),
                            modifier = Modifier.shimmering()
                        )
                        Text(
                            text = stringResource(id = R.string.ogrn, it.ogrn),
                            style = typography.body14.copy(
                                color = colors.text
                            ),
                            modifier = Modifier.shimmering()
                        )
                        Text(
                            text = stringResource(id = R.string.size_cost, it.sizeCost),
                            style = typography.body14.copy(
                                color = colors.text
                            ),
                            modifier = Modifier.shimmering()
                        )
                        Text(
                            text = stringResource(id = R.string.price_cost, it.priceCost),
                            style = typography.body14.copy(
                                color = colors.text
                            ),
                            modifier = Modifier.shimmering()
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EmployerCard(ip: FullIP?) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ip?.employer?.forEach {
            item {
                var state by remember {
                    mutableStateOf(CardFace.Front)
                }
                FlipCard(
                    cardFace = state,
                    onClick = {
                        state = it.next
                    },
                    axis = RotationAxis.AxisY,
                    back = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(
                                modifier = Modifier.weight(8f)
                            ) {
                                Text(
                                    text = stringResource(
                                        id = R.string.employer_actual_date,
                                        it.actualDate
                                    ),
                                    style = typography.body16.copy(
                                        color = colors.text
                                    ),
                                    modifier = Modifier.shimmering()
                                )
                                Text(
                                    text = stringResource(
                                        id = R.string.employer_appeal_recovery,
                                        it.appealRecovery
                                    ),
                                    style = typography.body16.copy(
                                        color = colors.text
                                    ),
                                    modifier = Modifier.shimmering()
                                )

                                Text(
                                    text = stringResource(
                                        id = R.string.employer_actual_date,
                                        it.actualDate
                                    ),
                                    style = typography.body16.copy(
                                        color = colors.text
                                    ),
                                    modifier = Modifier.shimmering()
                                )
                                Text(
                                    text = stringResource(
                                        id = R.string.employer_shpi,
                                        it.shpi
                                    ),
                                    style = typography.body16.copy(
                                        color = colors.text
                                    ),
                                    modifier = Modifier.shimmering()
                                )
                            }
                        }
                    },
                    front = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Work, contentDescription = "",
                                tint = if (it.appealRecovery == "ДА") colors.error else colors.onSecondary,
                                modifier = Modifier.weight(1f)
                            )
                            Column(
                                modifier = Modifier.weight(8f)
                            ) {
                                Text(
                                    text = stringResource(
                                        id = R.string.naming,
                                        it.employer
                                    ),
                                    style = typography.body16.copy(
                                        color = colors.text
                                    ),
                                    modifier = Modifier.shimmering()
                                )
                                Text(
                                    text = stringResource(
                                        id = R.string.address,
                                        it.employerAddress
                                    ),
                                    style = typography.body16.copy(
                                        color = colors.text
                                    ),
                                    modifier = Modifier.shimmering()
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PropertyCard(ip: FullIP?) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ip?.property?.forEach {
            item {
                var state by remember {
                    mutableStateOf(CardFace.Front)
                }
                FlipCard(
                    cardFace = state,
                    onClick = {
                        state = it.next
                    },
                    axis = RotationAxis.AxisY,
                    back = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(
                                modifier = Modifier.weight(8f)
                            ) {
                                Text(
                                    text = stringResource(
                                        id = R.string.property_arrested,
                                        if (it.arrested) "ДА" else "НЕТ"
                                    ),
                                    style = typography.body16.copy(
                                        color = colors.text
                                    ),
                                    modifier = Modifier.shimmering()
                                )
                                Text(
                                    text = stringResource(
                                        id = R.string.property_date_arrested,
                                        it.dateArrested
                                    ),
                                    style = typography.body16.copy(
                                        color = colors.text
                                    ),
                                    modifier = Modifier.shimmering()
                                )
                                Text(
                                    text = stringResource(
                                        id = R.string.property_price,
                                        it.price
                                    ),
                                    style = typography.body16.copy(
                                        color = colors.text
                                    ),
                                    modifier = Modifier.shimmering()
                                )
                            }
                        }
                    },
                    front = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.House, contentDescription = "",
                                tint = if (it.arrested) colors.error else colors.onSecondary,
                                modifier = Modifier.weight(1f)
                            )
                            Column(
                                modifier = Modifier.weight(8f)
                            ) {
                                Text(
                                    text = it.objectProperty,
                                    style = typography.body16.copy(
                                        color = colors.text
                                    ),
                                    modifier = Modifier.shimmering()
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun CardCard(ip: FullIP?) {
    val width = LocalConfiguration.current.screenWidthDp
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ip?.rs?.forEach {
            item {
                Row(
                    modifier = Modifier
                        .width((width * 0.75).dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = colors.cardColor),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CreditCard,
                        contentDescription = "",
                        tint = colors.onSecondary,
                        modifier = Modifier.weight(1f)
                    )
                    Column(
                        modifier = Modifier.weight(8f)
                    ) {
                        Text(
                            text = it.number,
                            style = typography.body16.copy(
                                color = colors.text
                            ),
                            modifier = Modifier.shimmering()
                        )
                        Text(
                            text = stringResource(id = R.string.type_rs, it.type),
                            style = typography.body14.copy(
                                color = colors.text
                            ),
                            modifier = Modifier.shimmering()
                        )
                        Text(
                            text = stringResource(id = R.string.bank_with_params, it.bank),
                            style = typography.body14.copy(
                                color = colors.text
                            ),
                            modifier = Modifier.shimmering()
                        )
                    }
                }
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
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ip?.auto?.forEach {
            item {
                FlipCard(
                    cardFace = state,
                    onClick = {
                        state = it.next
                    },
                    axis = RotationAxis.AxisY,
                    back = {
                        Row {
                            Column {
                                Text(
                                    text = stringResource(
                                        id = R.string.auto_date_arrested,
                                        it.dateOfArrestedTS
                                    ),
                                    style = typography.body14.copy(
                                        color = colors.text
                                    ),
                                    modifier = Modifier.shimmering()
                                )
                                Text(
                                    text = stringResource(id = R.string.price_TS, it.priceTS),
                                    style = typography.body14.copy(
                                        color = colors.text
                                    ),
                                    modifier = Modifier.shimmering()
                                )
                                Text(
                                    text = stringResource(id = R.string.storage, it.storage),
                                    style = typography.body14.copy(
                                        color = colors.text
                                    ),
                                    modifier = Modifier.shimmering()
                                )
                                Text(
                                    text = "",
                                    style = typography.body14.copy(
                                        color = colors.text
                                    ),
                                    modifier = Modifier.shimmering()
                                )
                            }
                        }
                    },
                    front = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.DirectionsCar, contentDescription = "",
                                tint = colors.onSecondary,
                                modifier = Modifier.weight(1f)
                            )
                            Column(
                                modifier = Modifier.weight(8f)
                            ) {
                                Text(
                                    text = it.auto,
                                    style = typography.body16.copy(
                                        color = colors.text
                                    ),
                                    modifier = Modifier.shimmering()
                                )
                                Text(
                                    text = stringResource(id = R.string.model, it.modelTS),
                                    style = typography.body14.copy(
                                        color = colors.text
                                    ),
                                    modifier = Modifier.shimmering()
                                )
                                Text(
                                    text = stringResource(id = R.string.gosnomer, it.gosNumber),
                                    style = typography.body14.copy(
                                        color = colors.text
                                    ),
                                    modifier = Modifier.shimmering()
                                )
                            }
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
    Surface(
        color = colors.background,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
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
                    tint = colors.onSecondary,
                )
                Icon(
                    imageVector = if (ip?.died == true) Icons.Rounded.SettingsAccessibility else Icons.Rounded.Check,
                    contentDescription = "",
                    tint = if (ip?.died == true) colors.error else colors.onSecondary,
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
                    tint = if (ip?.pensioner == true) colors.onSecondary else colors.error,
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
        color = colors.background,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
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
                    tint = colors.onSecondary,
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
    Surface(
        color = colors.background,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
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
                    tint = colors.onSecondary,
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
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.HMobiledata,
                contentDescription = "",
                tint = colors.onSecondary
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
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Surface(
            color = colors.cardColor,
            modifier = Modifier
                .weight(3f)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (ip?.balanceOwed?.contains("-") == true) {
                        Icon(
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = "",
                            tint = colors.error
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
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
                }
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

@Composable
fun QR(vCardText: String) {
    val width = LocalConfiguration.current.screenWidthDp
    val size = (width * 1.7).roundToInt()
    val context = LocalContext.current
    val hints = HashMap<EncodeHintType, Any?>().also {
        it[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.Q
        it[EncodeHintType.CHARACTER_SET] = "UTF-8"
        it[EncodeHintType.MARGIN] = 0
    }

    val bits = QRCodeWriter().encode(vCardText, BarcodeFormat.QR_CODE, size, size, hints)
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
        for (x in 0 until size) {
            for (y in 0 until size) {
                it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.GRAY)
            }
        }
    }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Image(bitmap = bitmap.asImageBitmap(), contentDescription = "QR")
        Icon(
            imageVector = Icons.Rounded.Share,
            contentDescription = "Share",
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .clickable {
                    context
                        .let { it1 -> FileUtils.with(it1, TypeFile.PICTURE) }
                        .share(bitmap, context as Activity)
                },
            tint = colors.onSecondary
        )
    }

}

