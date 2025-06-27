package com.abrosimov.account.presentation


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abrosimov.core.domain.Resource
import com.abrosimov.core.presentation.composableFunctions.CustomListItem
import com.abrosimov.account.R
import com.abrosimov.core.di.LocalViewModelFactory
import com.abrosimov.core.domain.models.Account


@Composable
fun AccountScreen(viewModel: AccountViewModel = viewModel(factory = LocalViewModelFactory.current)) {
    LaunchedEffect(Unit) { viewModel.loadAccount() }
    AccountBriefUI(viewModel)
}

@Composable
fun AccountBriefUI(viewModel: AccountViewModel) {
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
