package com.example.debit72.android.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.debit72.android.R
import com.example.debit72.android.presenter.theme.DebitTheme
import com.example.debit72.android.presenter.theme.typography

@Composable
fun ArrowBackWithSearch(
    navController: NavHostController,
    query: String,
    error: String?,
    onChangeText: (String) -> Unit,
    count: Int? = null,
    additionalFilter: @Composable (()-> Unit)? = null,
) {
    val modifier = Modifier.fillMaxWidth()
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        if (query.isBlank())
            focusRequester.requestFocus()
    }
    Row(modifier = modifier.padding(end = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Rounded.ArrowBackIos, contentDescription = "arrow back ios",
                tint = DebitTheme.colors.text
            )

        }
        DebitTextFieldDense(
            text = query,
            labelText = stringResource(id = R.string.search),
            maxLines = 1,
            modifier = modifier
                .height(62.dp)
                .focusRequester(focusRequester),
            enabled = true,
            trailingIcon = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        onChangeText.invoke("")
                    }) {
                        Icon(
                            Icons.Outlined.Clear,
                            contentDescription = "",
                            tint = DebitTheme.colors.primary
                        )
                    }
                    if (count != null)
                        Text(
                            text = count.toString(),
                            style = typography.bodyLarge16.copy(
                                color = DebitTheme.colors.primary,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    additionalFilter?.invoke()
                }
            },
            isClickable = false,
            onChange = {
                onChangeText.invoke(it)
            },
            isError = !error.isNullOrBlank(),
            errorMessage = error ?: ""
        )
    }
}