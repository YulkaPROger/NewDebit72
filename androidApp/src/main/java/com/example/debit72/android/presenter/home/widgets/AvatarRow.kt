package com.example.debit72.android.presenter.home.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.debit72.android.presenter.theme.DebitTheme
import com.example.debit72.android.presenter.theme.typography

@Composable
fun AvatarRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Surface(
                modifier = Modifier
                    .size(32.dp)
                    .border(
                        width = 16.dp,
                        color = DebitTheme.colors.gray700,
                        shape = RoundedCornerShape(16.dp)
                    ),
                color = Color.Transparent
            ) {}
            Text(
                text = "A", style = typography.titleMedium20.copy(
                    color = DebitTheme.colors.text
                )
            )
        }
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = "Арест", style = typography.titleMedium20.copy(
                color = DebitTheme.colors.text
            )
        )
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(16.dp)) {
            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = "arrow forward",
                tint = DebitTheme.colors.text
            )
        }
    }
}

@Preview()
@Composable
fun AvatarRowPreview() {
    AvatarRow()
}