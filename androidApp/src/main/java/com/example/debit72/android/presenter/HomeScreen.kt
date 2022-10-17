package com.example.debit72.android.presenter

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.debit72.android.R
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


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        val modifierRow = Modifier.weight(1f)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                NavigationButton(
                    imageVector = Icons.Default.Image,
                    name = R.string.ip_register,
                    modifier = modifierRow,
                    {}
                )
                NavigationButton(
                    imageVector = Icons.Default.Image,
                    name = R.string.auto,
                    modifier = modifierRow,
                    {}
                )
                NavigationButton(
                    imageVector = Icons.Default.Image,
                    name = R.string.contract_work,
                    modifier = modifierRow,
                    {}
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                NavigationButton(
                    imageVector = Icons.Default.Image,
                    name = R.string.claimants,
                    modifier = modifierRow,
                    {}
                )
                NavigationButton(
                    imageVector = Icons.Default.Image,
                    name = R.string.search_auto_real_time,
                    modifier = modifierRow,
                    {}
                )
                NavigationButton(
                    imageVector = Icons.Default.Image,
                    name = R.string.arrested_cars,
                    modifier = modifierRow,
                    {}
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                NavigationButton(
                    imageVector = Icons.Default.Image,
                    name = R.string.arrested_property,
                    modifier = modifierRow,
                    {}
                )
                NavigationButton(
                    imageVector = Icons.Default.Image,
                    name = R.string.claimants_on_ROSP,
                    modifier = modifierRow,
                    {}
                )
                Spacer(modifier = modifierRow)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val widthPieChart = 150.dp
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
                        Column (horizontalAlignment = Alignment.CenterHorizontally){
                            TextRow(
                                name = R.string.count,
                                info?.count?.toDouble()?.toInt().toString()
                            )
                        }
                        Column (horizontalAlignment = Alignment.CenterHorizontally) {
                            TextRow(name = R.string.balance_owed, info?.balanceOwed ?: "")
                        }
                    }

                }
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
            }
        }
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