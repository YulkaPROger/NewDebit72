package com.example.debit72.android.presenter.more

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Update
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.debit72.android.R
import com.example.debit72.android.data_store.UserSettings
import com.example.debit72.android.presenter.theme.DebitTheme
import com.example.debit72.android.presenter.theme.DebitTheme.colors
import com.example.debit72.android.presenter.theme.DebitTheme.typography
import com.example.debit72.android.widgets.DebitTextFieldDense
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoreScreen(navController: NavHostController, showSnackbar: (String, SnackbarDuration) -> Unit) {
    val store: MoreStore = viewModel(factory = MoreStoreFactory(LocalContext.current))
    val state = store.observeState().collectAsState().value

    when (state) {
        is MoreStore.State.LoadingError -> showSnackbar(
            state.error.message.toString(),
            SnackbarDuration.Short
        )
        else -> {}
    }
    var openDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val dataStore = UserSettings(context)
    if (openDialog) {
        AlertContent {
            openDialog = false
            if (it != null)
                store.dispatch(MoreStore.Action.SetApiKey(it))
        }
    }

    LazyColumn(contentPadding = PaddingValues(bottom = 56.dp)) {
        item {
            SettingsText()
        }
        item {
            GeneralSettings(state, store)
        }
        item {
            ApplicationLoginSettings(dataStore) {
                openDialog = true
            }
        }
        item {
            UpdateSettings(state, store, dataStore)
        }
    }

}

@Composable
fun AlertContent(onClick: (String?) -> Unit) {
    var api by remember {
        mutableStateOf("")
    }
    Dialog(
        onDismissRequest = {
            onClick.invoke(null)
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DebitTheme.colors.cardColor)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(id = R.string.set_api_key),
                    style = DebitTheme.typography.body16.copy(
                        color = DebitTheme.colors.text
                    ),
                )
                DebitTextFieldDense(
                    text = api,
                    onChange = {
                        api = it
                    },
                    labelText = stringResource(id = R.string.api_key),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                )
                Button(
                    onClick = { onClick.invoke(api) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = DebitTheme.colors.onSecondary,
                        contentColor = DebitTheme.colors.black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.save).uppercase())
                }
            }
        },
    )
}

@Composable
fun UpdateSettings(state: MoreStore.State?, store: MoreStore, dataStore: UserSettings) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = colors.cardColor)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
        ) {
            Text(
                text = stringResource(id = R.string.update_settings),
                style = typography.titleMedium20.copy(color = colors.text)
            )
            UpdateAll(state, store, dataStore)
            Spacer(modifier = Modifier.size(16.dp))
            UpdateIp(state, store, dataStore)
            Spacer(modifier = Modifier.size(16.dp))
            UpdateSpr(state, store, dataStore)
            Spacer(modifier = Modifier.size(16.dp))
            UpdateAuto(state, store, dataStore)
            Spacer(modifier = Modifier.size(16.dp))
            //TODO ?????????????????? ???? ???????? ??????????????, ?????????? ?????????????????????????? ?????????????????? ?? ???????? ?????????? ?????? 1000
//            UpdateArrestedAuto(state, store, dataStore)
//            Spacer(modifier = Modifier.size(16.dp))
//            UpdateArrestedProperty(state, store, dataStore)
        }
    }
}

@Composable
fun UpdateAll(state: MoreStore.State?, store: MoreStore, dataStore: UserSettings) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.update_all),
            style = typography.bodyLarge16.copy(color = colors.text)
        )
        Icon(
            Icons.Rounded.Update, contentDescription = "update",
            tint = colors.primaryVariant,
            modifier = Modifier.clickable {
                store.dispatch(MoreStore.Action.UpdateAll)
            }
        )
    }
    when (state) {
        is MoreStore.State.Loading -> {
            if (state.updateAll)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LinearProgressIndicator(
                        backgroundColor = colors.background,
                        color = colors.primaryVariant,
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                }
        }
        else -> {}
    }
}

@Composable
fun UpdateIp(state: MoreStore.State?, store: MoreStore, dataStore: UserSettings) {
    val countIP = dataStore.getString(UserSettings.COUNT_IP).collectAsState(initial = "")
    val dateUpdate = dataStore.getString(UserSettings.DATE_UPDATE_IP).collectAsState(initial = "")
    Text(
        text = stringResource(id = R.string.ip_register),
        style = typography.titleMedium20.copy(color = colors.text)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.last_update_date),
            style = typography.bodyLarge16.copy(color = colors.text)
        )
        Text(
            text = dateUpdate.value,
            style = typography.bodyLarge16.copy(color = colors.text),
            textAlign = TextAlign.End
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.last_update_count),
            style = typography.bodyLarge16.copy(color = colors.text)
        )
        Text(
            text = countIP.value,
            style = typography.bodyLarge16.copy(color = colors.text)
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.update),
            style = typography.bodyLarge16.copy(color = colors.text)
        )
        Icon(
            Icons.Rounded.Update, contentDescription = "update",
            tint = colors.primaryVariant,
            modifier = Modifier.clickable {
                store.dispatch(MoreStore.Action.UpdateIP)
            }
        )
    }
    when (state) {
        is MoreStore.State.Loading -> {
            if (state.loadingIP || state.updateAll)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LinearProgressIndicator(
                        backgroundColor = colors.background,
                        color = colors.primaryVariant,
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                }
        }
        else -> {}
    }
}

@Composable
fun UpdateSpr(state: MoreStore.State?, store: MoreStore, dataStore: UserSettings) {
    val countSPR = dataStore.getString(UserSettings.COUNT_SPR).collectAsState(initial = "")
    val dateUpdate = dataStore.getString(UserSettings.DATE_UPDATE_SPR).collectAsState(initial = "")
    Text(
        text = stringResource(id = R.string.spr_work_without_n),
        style = typography.titleMedium20.copy(color = colors.text)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.last_update_date),
            style = typography.bodyLarge16.copy(color = colors.text)
        )
        Text(
            text = dateUpdate.value,
            style = typography.bodyLarge16.copy(color = colors.text),
            textAlign = TextAlign.End
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.last_update_count),
            style = typography.bodyLarge16.copy(color = colors.text)
        )
        Text(
            text = countSPR.value,
            style = typography.bodyLarge16.copy(color = colors.text)
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.update),
            style = typography.bodyLarge16.copy(color = colors.text)
        )
        Icon(
            Icons.Rounded.Update, contentDescription = "update",
            tint = colors.primaryVariant,
            modifier = Modifier.clickable {
                store.dispatch(MoreStore.Action.UpdateSpr)
            }
        )
    }
    when (state) {
        is MoreStore.State.Loading -> {
            if (state.loadingSpr || state.updateAll)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LinearProgressIndicator(
                        backgroundColor = colors.background,
                        color = colors.primaryVariant,
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                }
        }
        else -> {}
    }
}

@Composable
fun UpdateAuto(state: MoreStore.State?, store: MoreStore, dataStore: UserSettings) {
    val countAuto = dataStore.getString(UserSettings.COUNT_AUTO).collectAsState(initial = "")
    val dateUpdate = dataStore.getString(UserSettings.DATE_UPDATE_AUTO).collectAsState(initial = "")
    Text(
        text = stringResource(id = R.string.auto),
        style = typography.titleMedium20.copy(color = colors.text)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.last_update_date),
            style = typography.bodyLarge16.copy(color = colors.text)
        )
        Text(
            text = dateUpdate.value,
            style = typography.bodyLarge16.copy(color = colors.text),
            textAlign = TextAlign.End
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.last_update_count),
            style = typography.bodyLarge16.copy(color = colors.text)
        )
        Text(
            text = countAuto.value,
            style = typography.bodyLarge16.copy(color = colors.text)
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.update),
            style = typography.bodyLarge16.copy(color = colors.text)
        )
        Icon(
            Icons.Rounded.Update, contentDescription = "update",
            tint = colors.primaryVariant,
            modifier = Modifier.clickable {
                store.dispatch(MoreStore.Action.UpdateAuto)
            }
        )
    }
    when (state) {
        is MoreStore.State.Loading -> {
            if (state.loadingAuto || state.updateAll)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LinearProgressIndicator(
                        backgroundColor = colors.background,
                        color = colors.primaryVariant,
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                }
        }
        else -> {}
    }
}

@Composable
fun UpdateArrestedAuto(state: MoreStore.State?, store: MoreStore, dataStore: UserSettings) {
    val countAuto =
        dataStore.getString(UserSettings.COUNT_ARRESTED_AUTO).collectAsState(initial = "")
    val dateUpdate =
        dataStore.getString(UserSettings.DATE_UPDATE_ARRESTED_AUTO).collectAsState(initial = "")
    Text(
        text = stringResource(id = R.string.arrested_cars),
        style = typography.titleMedium20.copy(color = colors.text)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.last_update_date),
            style = typography.bodyLarge16.copy(color = colors.text)
        )
        Text(
            text = dateUpdate.value,
            style = typography.bodyLarge16.copy(color = colors.text),
            textAlign = TextAlign.End
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.last_update_count),
            style = typography.bodyLarge16.copy(color = colors.text)
        )
        Text(
            text = countAuto.value,
            style = typography.bodyLarge16.copy(color = colors.text)
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.update),
            style = typography.bodyLarge16.copy(color = colors.text)
        )
        Icon(
            Icons.Rounded.Update, contentDescription = "update",
            tint = colors.primaryVariant,
            modifier = Modifier.clickable {
                store.dispatch(MoreStore.Action.UpdateArrestedAuto)
            }
        )
    }
    when (state) {
        is MoreStore.State.Loading -> {
            if (state.loadingArrestedAuto || state.updateAll)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LinearProgressIndicator(
                        backgroundColor = colors.background,
                        color = colors.primaryVariant,
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                }
        }
        else -> {}
    }
}

@Composable
fun UpdateArrestedProperty(state: MoreStore.State?, store: MoreStore, dataStore: UserSettings) {
    val countAuto =
        dataStore.getString(UserSettings.COUNT_ARRESTED_PROPERTY).collectAsState(initial = "")
    val dateUpdate =
        dataStore.getString(UserSettings.DATE_UPDATE_ARRESTED_PROPERTY).collectAsState(initial = "")
    Text(
        text = stringResource(id = R.string.arrested_property),
        style = typography.titleMedium20.copy(color = colors.text)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.last_update_date),
            style = typography.bodyLarge16.copy(color = colors.text)
        )
        Text(
            text = dateUpdate.value,
            style = typography.bodyLarge16.copy(color = colors.text),
            textAlign = TextAlign.End
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.last_update_count),
            style = typography.bodyLarge16.copy(color = colors.text)
        )
        Text(
            text = countAuto.value,
            style = typography.bodyLarge16.copy(color = colors.text)
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.update),
            style = typography.bodyLarge16.copy(color = colors.text)
        )
        Icon(
            Icons.Rounded.Update, contentDescription = "update",
            tint = colors.primaryVariant,
            modifier = Modifier.clickable {
                store.dispatch(MoreStore.Action.UpdateArrestedProperty)
            }
        )
    }
    when (state) {
        is MoreStore.State.Loading -> {
            if (state.loadingArrestedProperty || state.updateAll)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LinearProgressIndicator(
                        backgroundColor = colors.background,
                        color = colors.primaryVariant,
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                }
        }
        else -> {}
    }
}

@Composable
fun ApplicationLoginSettings(
    dataStore: UserSettings,
    onClick: () -> Unit
) {
    val countAuto =
        dataStore.getString(UserSettings.API_KEY).collectAsState(initial = "")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = colors.cardColor)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)

        ) {
            Text(
                text = stringResource(id = R.string.application_login),
                style = typography.titleMedium20.copy(color = colors.text)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onClick.invoke()
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.api_key),
                    style = typography.bodyLarge16.copy(color = colors.text)
                )
                Text(
                    text = countAuto.value,
                    style = typography.bodyLarge16.copy(color = colors.text)
                )

            }
        }

    }
}

@Composable
fun GeneralSettings(state: MoreStore.State?, store: MoreStore) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = UserSettings(context)
    val darkTheme = dataStore.getBoolean(UserSettings.DARK_THEME).collectAsState(initial = true)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = colors.cardColor)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.general_settings),
                style = typography.titleMedium20.copy(color = colors.text)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.them_settings),
                    style = typography.bodyLarge16.copy(color = colors.text)
                )
                Switch(
                    checked = darkTheme.value, onCheckedChange = {
                        scope.launch {
                            dataStore.setBoolean(it, UserSettings.DARK_THEME)
                        }
                    }, colors = SwitchDefaults.colors(
                        checkedThumbColor = colors.primary,
                        checkedTrackColor = colors.primaryVariant,
                        uncheckedThumbColor = colors.cardColor,
                        uncheckedTrackColor = colors.gray200
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
                    style = typography.bodyLarge16.copy(color = colors.text)
                )
                Switch(
                    checked = true, onCheckedChange = {}, colors = SwitchDefaults.colors(
                        checkedThumbColor = colors.primary,
                        checkedTrackColor = colors.primaryVariant,
                        uncheckedThumbColor = colors.cardColor,
                        uncheckedTrackColor = colors.gray200
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
            style = typography.titleMedium20.copy(
                color = colors.text
            )
        )
    }
}