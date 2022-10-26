package com.example.debit72.android.presenter.more

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.debit72.android.R
import com.example.debit72.android.presenter.theme.DebitTheme

@Composable
fun MoreScreen(navController: NavHostController) {
    LazyColumn() {
        item {
            SettingsText()
        }
        item {
            GeneralSettings()
        }
        item {
            ApplicationLoginSettings()
        }
    }
}

@Composable
fun ApplicationLoginSettings() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = DebitTheme.colors.gray900)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)

        ) {
            Text(
                text = stringResource(id = R.string.application_login),
                style = DebitTheme.typography.titleMedium20.copy(color = DebitTheme.colors.text)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.api_key),
                    style = DebitTheme.typography.bodyLarge16.copy(color = DebitTheme.colors.text)
                )
                Text(
                    text = stringResource(id = R.string.stars),
                    style = DebitTheme.typography.bodyLarge16.copy(color = DebitTheme.colors.text)
                )

            }
        }

    }
}

@Composable
fun GeneralSettings() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = DebitTheme.colors.gray900)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
            ,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.general_settings),
                style = DebitTheme.typography.titleMedium20.copy(color = DebitTheme.colors.text)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.them_settings),
                    style = DebitTheme.typography.bodyLarge16.copy(color = DebitTheme.colors.text)
                )
                Switch(
                    checked = true, onCheckedChange = {}, colors = SwitchDefaults.colors(
                        checkedThumbColor = DebitTheme.colors.primary,
                        checkedTrackColor = DebitTheme.colors.primaryVariant,
                        uncheckedThumbColor = DebitTheme.colors.gray700,
                        uncheckedTrackColor = DebitTheme.colors.gray200
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.language),
                    style = DebitTheme.typography.bodyLarge16.copy(color = DebitTheme.colors.text)
                )
                Switch(
                    checked = false, onCheckedChange = {}, colors = SwitchDefaults.colors(
                        checkedThumbColor = DebitTheme.colors.primary,
                        checkedTrackColor = DebitTheme.colors.primaryVariant,
                        uncheckedThumbColor = DebitTheme.colors.gray700,
                        uncheckedTrackColor = DebitTheme.colors.gray200
                    )
                )
            }
        }
    }
}


@Composable
fun SettingsText() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.settings),
            style = DebitTheme.typography.titleMedium20.copy(
                color = DebitTheme.colors.text
            )
        )
    }
}