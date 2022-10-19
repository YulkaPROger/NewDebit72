package com.example.debit72.android.presenter

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.debit72.android.R
import com.example.debit72.android.presenter.theme.DebitTheme
import com.example.debit72.android.presenter.theme.DebitTheme.colors
import com.example.debit72.android.presenter.theme.typography
import com.example.debit72.android.widgets.NavigationButton
import com.example.debit72.android.widgets.pie.PieChart
import com.example.debit72.android.widgets.pie.PieChartDataModel
import com.example.debit72.android.widgets.pie.SimpleSliceDrawer
import com.example.debit72.android.widgets.pie.ValueProfit
import com.example.debit72.repository.InfoRepository
import kotlinx.coroutines.launch
import model.GeneralInformation

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        val fillWidth = Modifier.fillMaxWidth()

        Column(
            modifier = fillWidth,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AvatarRow()
            AutoNumber()
            StoriesRow()
            PieChartAndData()
        }


    }
}

@Composable
fun AutoNumber() {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(2.dp)
                .background(colors.black87)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Surface(
                    modifier = Modifier
                        .size(200.dp, 50.dp)
                        .border(
                            width = 4.dp,
                            color = DebitTheme.colors.black54,
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                }
                Text(
                    text = "v 000 VV", style = typography.autoNumberStyle.copy(
                        color = DebitTheme.colors.black54
                    )
                )
            }
            Box(contentAlignment = Alignment.Center) {
                Surface(
                    modifier = Modifier
                        .size(50.dp, 50.dp)
                        .border(
                            width = 4.dp,
                            color = DebitTheme.colors.black54,
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                }
                Text(
                    text = "72", style = typography.autoNumberStyle.copy(
                        color = DebitTheme.colors.black54
                    )
                )
            }
        }
    }
}

@Composable
fun PieChartAndData() {
    val scope = rememberCoroutineScope()
    var info: GeneralInformation? by remember { mutableStateOf(null) }
    var pieChartDataModel: PieChartDataModel? by remember {
        mutableStateOf(
            null
        )
    }
    LaunchedEffect(true) {
        scope.launch {
            info = InfoRepository().getInfo()
            info?.let {
                pieChartDataModel = PieChartDataModel(
                    listOf(
                        ValueProfit(
                            value = it.balanceOwed.toDouble(),
                            name = R.string.balance_owed
                        ),
                        ValueProfit(
                            value = it.totalDebt.toDouble(),
                            name = R.string.total_debt
                        ),
                        ValueProfit(
                            value = it.amountOfDuty.toDouble(),
                            name = R.string.amount_of_duty
                        ),
                        ValueProfit(
                            value = it.amountOfLegalServices.toDouble(),
                            name = R.string.amount_of_legal_services
                        ),
                        ValueProfit(
                            value = it.amountPenalty.toDouble(),
                            name = R.string.amount_penalty
                        ),
                        ValueProfit(
                            value = it.amountOfCommunalServices.toDouble(),
                            name = R.string.amount_of_communal_services
                        ),
                    )
                )
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        val widthPieChart = 150.dp
        info?.let {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.End
            ) {
                TextRow(name = R.string.total_debt, it.totalDebt)
                TextRow(name = R.string.amount_of_duty, it.amountOfDuty)
                TextRow(name = R.string.amount_of_legal_services, it.amountOfLegalServices)
                TextRow(name = R.string.amount_penalty, it.amountPenalty)
                TextRow(
                    name = R.string.amount_of_communal_services,
                    it.amountOfCommunalServices
                )
            }
        }
        if (pieChartDataModel != null) {
            Column {
                PieChart(
                    modifier = Modifier
                        .size(widthPieChart),
                    pieChartData = pieChartDataModel!!.pieChartData,
                    sliceDrawer = SimpleSliceDrawer(sliceThickness = pieChartDataModel!!.sliceThickness),
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.width(widthPieChart)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        TextRow(
                            name = R.string.count,
                            info?.count?.toDouble()?.toInt().toString()
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        TextRow(name = R.string.balance_owed, info?.balanceOwed ?: "")
                    }
                }
            }
        }
    }
}

@Composable
fun StoriesRow() {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val storisWidth = Modifier.size((screenWidth / 3.5).dp)
    LazyRow() {
        items(1) {
            NavigationButton(
                imageVector = Icons.Default.Image,
                name = R.string.ip_register,
                modifier = storisWidth,
                {}
            )
            NavigationButton(
                imageVector = Icons.Default.Image,
                name = R.string.auto,
                modifier = storisWidth,
                {}
            )
            NavigationButton(
                imageVector = Icons.Default.Image,
                name = R.string.contract_work,
                modifier = storisWidth,
                {}
            )
            NavigationButton(
                imageVector = Icons.Default.Image,
                name = R.string.claimants,
                modifier = storisWidth,
                {}
            )
            NavigationButton(
                imageVector = Icons.Default.Image,
                name = R.string.search_auto_real_time,
                modifier = storisWidth,
                {}
            )
            NavigationButton(
                imageVector = Icons.Default.Image,
                name = R.string.arrested_cars,
                modifier = storisWidth,
                {}
            )
            NavigationButton(
                imageVector = Icons.Default.Image,
                name = R.string.arrested_property,
                modifier = storisWidth,
                {}
            )
            NavigationButton(
                imageVector = Icons.Default.Image,
                name = R.string.claimants_on_ROSP,
                modifier = storisWidth,
                {}
            )
        }
    }
}

@Composable
fun AvatarRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(contentAlignment = Alignment.Center) {
            Surface(
                modifier = Modifier
                    .size(32.dp)
                    .background(DebitTheme.colors.promoPrice)
                    .border(
                        width = 16.dp,
                        color = DebitTheme.colors.promoPrice,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {

            }
            Text(text = "A", style = typography.titleMedium16)
        }
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = "Arests", style = typography.titleMedium16)
    }
}

@Composable
fun TextRow(name: Int, value: String) {
    Text(
        text = stringResource(id = name),
        style = TextStyle(fontSize = 10.sp)
    )
    Text(
        text = value,
        style = TextStyle(fontSize = 10.sp)
    )
}