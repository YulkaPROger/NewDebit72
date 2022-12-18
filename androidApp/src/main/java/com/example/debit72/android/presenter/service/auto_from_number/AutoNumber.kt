package com.example.debit72.android.presenter.service.auto_from_number

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CarRental
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.debit72.android.R
import com.example.debit72.android.presenter.theme.DebitTheme
import kotlinx.coroutines.delay
import model.AutoFromNumber

@Composable
fun AutoNumberScreen(navController: NavHostController) {
    val store = viewModel<AutoFromNumberStore>()
    val width = LocalConfiguration.current.screenWidthDp
    var text by remember {
        mutableStateOf("")
    }
    var error: String? by remember {
        mutableStateOf(null)
    }
    var loading: Boolean by remember {
        mutableStateOf(false)
    }
    var autoList: List<AutoFromNumber> by remember { mutableStateOf(emptyList()) }
    val state = store.observeState().collectAsState().value
    when (state) {
        is AutoFromNumberStore.State.Data -> {
            autoList = state.autoList
            loading = false
        }
        AutoFromNumberStore.State.Loading -> {
            loading = true
            autoList = emptyList()
        }
        is AutoFromNumberStore.State.LoadingError -> {
            error = state.error
            loading = true
            autoList = emptyList()
        }
        null -> {}
    }
    LaunchedEffect(text) {
        if (text.count() > 1) {
            delay(1000)
            store.dispatch(AutoFromNumberStore.Action.Search(text))
        } else store.dispatch(AutoFromNumberStore.Action.Clear)
    }
    LaunchedEffect(key1 = Unit) {
        when (state) {
            is AutoFromNumberStore.State.Data -> {
                text = state.query
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.number),
                        contentDescription = "number",
                        modifier = Modifier
                            .width((width / 1.4).dp)
                            .padding(top = 12.dp)
                    )

                    BasicTextField(
                        value = text.uppercase(),
                        onValueChange = { text = it },
                        modifier = Modifier
                            .padding(start = 16.dp),
                        visualTransformation = { mobileNumberFilter(it) },
                        textStyle = MaterialTheme.typography.h4.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            capitalization = KeyboardCapitalization.Characters
                        )
                    )

                }
                Text(
                    text = autoList.size.toString(),
                    style = DebitTheme.typography.titleMedium20.copy(
                        color = DebitTheme.colors.primaryVariant,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        if (loading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = DebitTheme.colors.primaryVariant,
                )
            }
        }
        error?.let {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = it, style = DebitTheme.typography.body16.copy(
                        color = DebitTheme.colors.error
                    )
                )
            }
        }
        LazyColumn(
            contentPadding = PaddingValues(bottom = 56.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            autoList.forEach {
                item {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .border(
                                width = 2.dp,
                                color = DebitTheme.colors.surface,
                                RoundedCornerShape(12.dp)
                            )
                            .background(DebitTheme.colors.cardColor)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.gos_number, it.gosNumber),
                                style = DebitTheme.typography.body16.copy(
                                    color = DebitTheme.colors.text
                                )
                            )
                            Text(
                                text = stringResource(id = R.string.debtor, it.owner),
                                style = DebitTheme.typography.body16.copy(
                                    color = DebitTheme.colors.text
                                )
                            )
                            Text(
                                text = stringResource(id = R.string.ts_debtor, it.tsOwner),
                                style = DebitTheme.typography.body14.copy(
                                    color = DebitTheme.colors.text
                                )
                            )
                            if (it.arrested)
                                Column() {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Rounded.CarRental,
                                            contentDescription = "car",
                                            tint = DebitTheme.colors.primaryVariant
                                        )
                                        Text(
                                            text = stringResource(
                                                id = R.string.date_arrested,
                                                it.dateArrested
                                            ),
                                            style = DebitTheme.typography.body14.copy(
                                                color = DebitTheme.colors.text
                                            )
                                        )
                                    }
                                    Text(
                                        text = stringResource(
                                            id = R.string.storage,
                                            it.storage
                                        ),
                                        style = DebitTheme.typography.body14.copy(
                                            color = DebitTheme.colors.text
                                        )
                                    )
                                }
                            if (it.tsRealised)
                                Text(
                                    text = stringResource(
                                        id = R.string.date_realized,
                                        it.stateRealised
                                    ),
                                    style = DebitTheme.typography.body14.copy(
                                        color = DebitTheme.colors.text
                                    )
                                )
                            Text(
                                text = stringResource(
                                    id = R.string.price,
                                    it.sum
                                ),
                                style = DebitTheme.typography.body14.copy(
                                    color = DebitTheme.colors.text
                                )
                            )
                            Text(
                                text = stringResource(
                                    id = R.string.comment,
                                    it.comment
                                ),
                                style = DebitTheme.typography.body14.copy(
                                    color = DebitTheme.colors.text
                                )
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                it.ip.forEach { ip ->
                                    item {
                                        Column(
                                            modifier = Modifier
                                                .clickable {
                                                    navController.navigate("fullIP/${ip.number}")
                                                }
                                                .clip(RoundedCornerShape(12.dp))
                                                .border(
                                                    width = 2.dp,
                                                    color = DebitTheme.colors.surface,
                                                    RoundedCornerShape(12.dp)
                                                )
                                                .background(DebitTheme.colors.cardColor)
                                                .padding(8.dp)
                                        ) {
                                            Text(
                                                text = stringResource(
                                                    id = R.string.reg_number_ip,
                                                    ip.regNumber
                                                ),
                                                style = DebitTheme.typography.body14.copy(
                                                    color = DebitTheme.colors.text
                                                )
                                            )
                                            Text(
                                                text = stringResource(
                                                    id = R.string.total_debt,
                                                    ip.totalDebt
                                                ),
                                                style = DebitTheme.typography.body14.copy(
                                                    color = DebitTheme.colors.text
                                                )
                                            )
                                            Text(
                                                text = stringResource(
                                                    id = R.string.balance_owed_with_param,
                                                    ip.balanceOwed
                                                ),
                                                style = DebitTheme.typography.body14.copy(
                                                    color = DebitTheme.colors.text
                                                )
                                            )
                                            Text(
                                                text = stringResource(
                                                    id = R.string.rosp,
                                                    ip.rosp
                                                ),
                                                style = DebitTheme.typography.body14.copy(
                                                    color = DebitTheme.colors.text
                                                )
                                            )
                                            Text(
                                                text = stringResource(
                                                    id = R.string.spi,
                                                    ip.spi
                                                ),
                                                style = DebitTheme.typography.body14.copy(
                                                    color = DebitTheme.colors.text
                                                )
                                            )
                                            Text(
                                                text = ip.claimant,
                                                style = DebitTheme.typography.body14.copy(
                                                    color = DebitTheme.colors.text
                                                )
                                            )
                                            Text(
                                                text = stringResource(
                                                    id = R.string.address,
                                                    ip.address
                                                ),
                                                style = DebitTheme.typography.body14.copy(
                                                    color = DebitTheme.colors.text
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

const val mask = "x xxx xx    xxх"

fun mobileNumberFilter(text: AnnotatedString): TransformedText {
    val trimmed =
        if (text.text.length >= 9) text.text.substring(0..8) else text.text

    val annotatedString = AnnotatedString.Builder().run {
        for (i in trimmed.indices) {
            append(trimmed[i])
            if (i == 0 || i == 3) {
                append(" ")
            }
            if (i == 5) {
                append(" ")
                append(" ")
                append(" ")
                append(" ")
            }
        }
        pushStyle(SpanStyle(color = Color.Transparent))
        append(mask.takeLast(mask.length - length))
        toAnnotatedString()
    }

    val phoneNumberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 0) return offset
            if (offset <= 3) return offset + 1
            if (offset <= 5) return offset + 2
            if (offset <= 9) return offset + 6
            return 12
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 0) return offset
            if (offset <= 3) return offset - 1
            if (offset <= 5) return offset - 2
            if (offset <= 9) return offset - 6
            return 9
        }
    }

    return TransformedText(annotatedString, phoneNumberOffsetTranslator)
}