package com.example.debit72.android.presenter.home.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.debit72.android.R
import com.example.debit72.android.presenter.Shimmering
import com.example.debit72.android.presenter.shimmering
import com.example.debit72.android.presenter.theme.DebitTheme
import com.example.debit72.repository.InfoRepository
import kotlinx.coroutines.launch
import model.GeneralInformation


@Composable
fun ColumnData() {
    val scope = rememberCoroutineScope()
    var info: GeneralInformation? by remember { mutableStateOf(null) }
    LaunchedEffect(true) {
        scope.launch {
            info = InfoRepository().getInfo()
        }
    }
    val width = LocalConfiguration.current.screenWidthDp
    Shimmering(isVisible = info == null) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Box(
                modifier = Modifier
                    .size((width / 3).dp)
                    .weight(1F)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = DebitTheme.colors.cardColor)
            ) {
                Column() {
                    Text(
                        text = stringResource(id = R.string.count),
                        style = DebitTheme.typography.titleMedium20.copy(
                            color = DebitTheme.colors.text
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
                    )
                    Text(
                        text = info?.count?.toDouble()?.toInt().toString(),
                        style = DebitTheme.typography.bodyLarge16.copy(
                            color = DebitTheme.colors.text
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                            .shimmering()
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.loon_icon_2),
                    contentDescription = "count icon",
                    modifier = Modifier
                        .padding(16.dp)
                        .size(48.dp)
                        .align(Alignment.BottomEnd)
                )

            }

            Box(
                modifier = Modifier
                    .size((width / 3).dp)
                    .weight(1F)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = DebitTheme.colors.cardColor)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.balance_to_total),
                        style = DebitTheme.typography.body16.copy(
                            color = DebitTheme.colors.text
                        ),
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp)
                    )
                    Text(
                        text = info?.totalDebt + " /\n" + info?.balanceOwed,
                        style = DebitTheme.typography.body12.copy(
                            color = DebitTheme.colors.text
                        ),
                        modifier = Modifier
                            .padding(vertical = 4.dp, horizontal = 16.dp)
                            .shimmering()
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        Surface(
                            color = DebitTheme.colors.onSurface,
                            shape = RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp),
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text(text = "")
                        }
                        Surface(
                            color = DebitTheme.colors.surface,
                            shape = RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp),
                            modifier = Modifier
                                .weight(0.456f)
                                .padding(bottom = 4.dp)
                        ) {
                            Text(text = "")

                        }
                    }

                }
            }
        }
        RowWithOnceData(width, R.string.total_debt, info?.totalDebt)
        RowWithOnceData(width, R.string.balance_owed, info?.balanceOwed)
        RowWithOnceData(width, R.string.amount_of_communal_services, info?.amountOfCommunalServices)
        RowWithOnceData(width, R.string.amount_penalty, info?.amountPenalty)
        RowWithOnceData(width, R.string.amount_of_duty, info?.amountOfDuty)
        RowWithOnceData(width, R.string.amount_of_legal_services, info?.amountOfLegalServices)
    }
}


@Composable
fun RowWithOnceData(width: Int, name: Int, value: String?) {
    Surface(modifier = Modifier.fillMaxWidth(), color = DebitTheme.colors.background) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .height((width / 4).dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color = DebitTheme.colors.cardColor),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Image(
                painter = painterResource(id = R.drawable.rus),
                contentDescription = "count icon",
                modifier = Modifier
                    .padding(16.dp)
                    .size(48.dp)
            )
            Column() {
                Text(
                    text = stringResource(id = name),
                    style = DebitTheme.typography.bodyNormal18.copy(
                        color = DebitTheme.colors.text
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
                Text(
                    text = value ?: Math.random().toString(),
                    style = DebitTheme.typography.bodyLarge16.copy(
                        color = DebitTheme.colors.text
                    ),
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .shimmering()
                )
            }
        }
    }

}

@Composable
fun TextRow(name: Int, value: String) {
    Text(
        text = stringResource(id = name),
        style = DebitTheme.typography.body10.copy(
            color = DebitTheme.colors.text
        )
    )
    Text(
        text = value,
        style = DebitTheme.typography.body10.copy(
            color = DebitTheme.colors.text
        )
    )
}
