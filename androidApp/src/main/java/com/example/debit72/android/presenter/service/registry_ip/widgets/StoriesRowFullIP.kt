package com.example.debit72.android.presenter.service.registry_ip.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.debit72.android.R
import com.example.debit72.android.presenter.theme.DebitTheme
import model.FullIP

@Composable
fun StoriesRowFullIP(ip: FullIP?) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        item {
            StoriesIp(
                title = stringResource(
                    id = R.string.fond
                ),
                content = {
                    Text(
                        text = ip?.address ?: "aggregate2",
                        style = DebitTheme.typography.titleMedium20.copy(
                            color = DebitTheme.colors.text,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
        item {
            StoriesIp(
                title = stringResource(
                    id = R.string.ls
                ),
                content = {
                    Text(
                        text = ip?.ls ?: "hjvkjkb",
                        style = DebitTheme.typography.titleMedium20.copy(
                            color = DebitTheme.colors.text,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
        item {
            StoriesIp(
                title = stringResource(
                    id = R.string.penalties_for_ID
                ),
                content = {
                    Text(
                        text = "ЖКУ: ${ip?.ZHKU}\nПени: ${ip?.penalties}\n" +
                                "Пошлина: ${ip?.duty}\n" +
                                "Юр.услуги: ${ip?.yurServices}",
                        style = DebitTheme.typography.bodyNormal18.copy(
                            color = DebitTheme.colors.text,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
        item {
            StoriesIp(
                title = stringResource(
                    id = R.string.period
                ),
                content = {
                    Text(
                        text = "c:  ${ip?.periodFrom}\nпо:  ${ip?.periodTo}",
                        style = DebitTheme.typography.bodyNormal18.copy(
                            color = DebitTheme.colors.text,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
        item {
            StoriesIp(
                title = stringResource(
                    id = R.string.rosp
                ),
                content = {
                    Text(
                        text = stringResource(
                            R.string.date_of_explication,
                            ip?.dateOfApplication ?: "hjvkb",
                            ip?.dateExcitation ?: "hjvkb",
                            ip?.dateCancelledIP ?: "dfghjgh"
                        ),
                        style = DebitTheme.typography.body16.copy(
                            color = DebitTheme.colors.text,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
        item{
            StoriesIp(
                title = stringResource(
                    id = R.string.bank
                ),
                content = {
                    Text(
                        text = stringResource(
                            id = R.string.bank_status, ip?.dateSubmissionBank ?: "vbn",
                            ip?.dateRevokedBank ?: "CFVGHJ", ip?.bankStatus ?: "vbnm"
                        ),
                        style = DebitTheme.typography.body16.copy(
                            color = DebitTheme.colors.text,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    }
}
