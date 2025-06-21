package com.abrosimov.financeapp.ui.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.abrosimov.financeapp.R
import com.abrosimov.financeapp.domain.models.Account
import com.abrosimov.financeapp.domain.repo.Resource
import com.abrosimov.financeapp.ui.FinanceViewModel
import com.abrosimov.financeapp.ui.lists.CustomListItem


@Composable
fun AccountScreen(viewModel: FinanceViewModel) {
    AccountBriefUI(viewModel)
}

@Composable
fun AccountBriefUI(viewModel: FinanceViewModel) {
    val state = viewModel.accountState.collectAsState()
    when (state.value) {
        is Resource.Error -> {
            Column {
                Text("Ошибка: ${(state.value as Resource.Error).message}")
                Button(onClick = viewModel::loadAccount) {
                    Text("Повторить")
                }
            }
        }

        Resource.Loading -> {
            CircularProgressIndicator()
        }

        is Resource.Success -> {
            val account = (state.value as Resource.Success<Account>).data
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Balance("${account.balance} ${account.currency}")
                Currency(account.currency)
            }
        }
    }
}

@Composable
fun Balance(balance: String) {
    CustomListItem(
        leftTitle = "Баланс",
        rightTitle = balance,
        leftIcon = "\uD83D\uDCB0",
        listBackground = MaterialTheme.colorScheme.secondary,
        rightIcon = R.drawable.ic_drill_in,
        leftIconBackground = MaterialTheme.colorScheme.background,
        clickable = true,
        listHeight = 56
    )
    HorizontalDivider()
}

@Composable
fun Currency(currency: String) {
    CustomListItem(
        leftTitle = "Валюта",
        rightTitle = currency,
        listBackground = MaterialTheme.colorScheme.secondary,
        leftIconBackground = MaterialTheme.colorScheme.background,
        clickable = true,
        rightIcon = R.drawable.ic_drill_in,
        listHeight = 56
    )
}
