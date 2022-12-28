package com.example.debit72.android.presenter.additional_service

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.debit72.android.R
import com.example.debit72.android.presenter.theme.DebitTheme
import com.example.debit72.repository.AdditionalSerciceRepository
import kotlinx.coroutines.launch
import model.Task

@Composable
fun TasksScreen(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    var tasksList: List<Task>? by remember { mutableStateOf(null) }

    var error: String? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(Unit) {
        scope.launch {
            kotlin.runCatching {
                AdditionalSerciceRepository().tasks()
            }.onSuccess {
                tasksList = it
            }.onFailure {
                tasksList = emptyList()
                error = it.message
            }
        }
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIos, contentDescription = "arrow back ios",
                    tint = DebitTheme.colors.text
                )

            }
        }
        LazyColumn(
            contentPadding = PaddingValues(bottom = 56.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            tasksList?.forEach {
                item {
                    TasksCard(it)
                }
            }
        }
    }
}

@Composable
fun TasksCard(task: Task) {
    val modifier = Modifier.fillMaxWidth()
    Surface(
        modifier = modifier
            .clip(
                RoundedCornerShape(8.dp)
            ),
        color = DebitTheme.colors.cardColor
    ) {
        Column(modifier = modifier.padding(8.dp)) {
            Text(
                text = stringResource(
                    id = R.string.executor,
                    task.executor
                ),
                style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text),
            )
            Text(
                text = stringResource(
                    id = R.string.task,
                    task.name
                ),
                style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text),
            )
            Text(
                text = stringResource(
                    id = R.string.description,
                    task.description
                ),
                style = DebitTheme.typography.body16.copy(color = DebitTheme.colors.text)
            )
        }
    }
}