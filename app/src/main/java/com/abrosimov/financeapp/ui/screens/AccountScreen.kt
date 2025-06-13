package com.abrosimov.financeapp.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abrosimov.financeapp.R
import com.abrosimov.financeapp.domain.models.AccountBrief
import com.abrosimov.financeapp.ui.misc.CustomListItem
import com.abrosimov.financeapp.ui.theme.FinanceAppTheme


@Composable
fun AccountScreen() {


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AccountBriefUI()
    }
}

@Composable
fun AccountBriefUI() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        CustomListItem(
            headlineContent = @Composable { Text("Баланс") },
            leadingContent = {
                Text(
                    text = "\uD83D\uDCB0",
                    modifier = Modifier.background(
                        MaterialTheme.colorScheme.background,
                        CircleShape
                    )
                )
            },
            trailingContent = {
                Row(
                    modifier = Modifier.height(IntrinsicSize.Min),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("-670 000 ₽", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_drill_in),
                        contentDescription = "Подробности"
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.secondary,
            addDivider = true
        )

        CustomListItem(
            headlineContent = @Composable { Text("Валюта") },
            trailingContent = {
                Row(
                    modifier = Modifier.height(IntrinsicSize.Min),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("₽", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_drill_in),
                        contentDescription = "Подробности"
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.secondary,
            addDivider = true
        )
    }
}

@Preview
@Composable
fun ListItemUI() {
    FinanceAppTheme {
        CustomListItem(
            headlineContent = @Composable { Text("Валюта") },
            trailingContent = {
                Row(
                    modifier = Modifier.height(IntrinsicSize.Min),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("₽", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_drill_in),
                        contentDescription = "Подробности"
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.secondary,
            addDivider = true
        )
    }
}