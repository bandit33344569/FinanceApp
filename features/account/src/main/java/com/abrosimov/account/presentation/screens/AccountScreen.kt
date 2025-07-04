package com.abrosimov.account.presentation.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abrosimov.core.domain.Resource
import com.abrosimov.core.presentation.composableFunctions.CustomListItem
import com.abrosimov.account.R
import com.abrosimov.account.presentation.viewmodel.AccountViewModel
import com.abrosimov.core.di.LocalViewModelFactory
import com.abrosimov.core.domain.models.Account


@Composable
fun AccountScreen(
    viewModel: AccountViewModel = viewModel(factory = LocalViewModelFactory.current,key = "shared_account_viewmodel"),
) {
    LaunchedEffect(Unit) { viewModel.loadAccount() }
    AccountBriefUI(viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountBriefUI(viewModel: AccountViewModel) {
    val modalSheetState = rememberModalBottomSheetState()
    var showBottomSheet = remember { mutableStateOf(false) }
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
                Balance("${account.balance} ${account.currency}", onClick = {})
                Currency(
                    account.currency,
                    onClick = { showBottomSheet.value = true }
                )
            }
            if (showBottomSheet.value) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet.value = false },
                    sheetState = modalSheetState
                ) {
                    CustomListItem(
                        leftTitle = "Российский рубль ₽",
                        leftIcon = "₽",
                        listHeight = 70,
                        listBackground = MaterialTheme.colorScheme.background,
                        leftIconBackground = MaterialTheme.colorScheme.background,
                        clickable = true,
                        onClick = {
                            viewModel.updateCurrency("RUB")
                            showBottomSheet.value = false
                        }
                    )
                    CustomListItem(
                        leftTitle = "Американский доллар $",
                        leftIcon = "$",
                        listHeight = 70,
                        listBackground = MaterialTheme.colorScheme.background,
                        leftIconBackground = MaterialTheme.colorScheme.background,
                        clickable = true,
                        onClick = {
                            viewModel.updateCurrency("USD")
                            showBottomSheet.value = false
                        }
                    )
                    CustomListItem(
                        leftTitle = "Евро €",
                        leftIcon = "€",
                        listHeight = 70,
                        listBackground = MaterialTheme.colorScheme.background,
                        leftIconBackground = MaterialTheme.colorScheme.background,
                        clickable = true,
                        onClick = {
                            viewModel.updateCurrency("EUR")
                            showBottomSheet.value = false
                        }
                    )
                    CustomListItem(
                        leftTitle = "Отмена",
                        leftIcon = "⊗",
                        listHeight = 70,
                        listBackground = MaterialTheme.colorScheme.error,
                        leftIconBackground = MaterialTheme.colorScheme.error,
                        clickable = true,
                        onClick = { showBottomSheet.value = false }
                    )
                }
            }
        }
    }
}


@Composable
fun Balance(balance: String, onClick: () -> Unit) {
    CustomListItem(
        leftTitle = "Баланс",
        rightTitle = balance,
        leftIcon = "\uD83D\uDCB0",
        listBackground = MaterialTheme.colorScheme.secondary,
        rightIcon = R.drawable.ic_drill_in,
        leftIconBackground = MaterialTheme.colorScheme.background,
        clickable = true,
        listHeight = 56,
        onClick = onClick
    )
    HorizontalDivider()
}

@Composable
fun Currency(
    currency: String,
    onClick: () -> Unit
) {
    CustomListItem(
        leftTitle = "Валюта",
        rightTitle = currency,
        listBackground = MaterialTheme.colorScheme.secondary,
        leftIconBackground = MaterialTheme.colorScheme.background,
        clickable = true,
        rightIcon = R.drawable.ic_drill_in,
        listHeight = 56,
        onClick = onClick
    )
}
