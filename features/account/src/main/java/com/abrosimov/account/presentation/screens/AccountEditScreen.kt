package com.abrosimov.account.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abrosimov.account.presentation.viewmodel.AccountViewModel
import com.abrosimov.account.R
import com.abrosimov.account.di.components.DaggerAccountComponent
import com.abrosimov.account.di.dependencies.AccountDependenciesStore
import com.abrosimov.account.presentation.dialogs.AccountNameDialog
import com.abrosimov.account.presentation.dialogs.BalanceDialog
import com.abrosimov.impl.models.Account
import com.abrosimov.ui.composableFunctions.CustomListItem
import com.abrosimov.ui.viewmodel.SharedAppViewModel
import com.abrosimov.utils.models.Resource

@Composable
fun AccountEditScreen(
) {
    val accountComponent =
        remember {
            DaggerAccountComponent
                .builder()
                .dependencies(accountDependencies = AccountDependenciesStore.accountDependencies)
                .build()
        }
    val accountViewModel = viewModel<AccountViewModel>(factory = accountComponent.accountViewModelFactory)
    val sharedAppViewModel = viewModel<SharedAppViewModel>(factory = accountComponent.sharedAppViewModelFactory())

    var showNameDialog = remember { mutableStateOf(false) }
    var showBalanceDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        sharedAppViewModel.saveEvent.collect { event ->
            accountViewModel.saveAccount()
        }
    }

    val editedAccount = accountViewModel.editedAccount.collectAsStateWithLifecycle()
    val state = accountViewModel.accountState.collectAsStateWithLifecycle()
    when (state.value) {
        is Resource.Error -> {
            Column {
                Text("Ошибка: ${(state.value as Resource.Error).message}")
                Button(onClick = accountViewModel::loadAccount) {
                    Text("Повторить")
                }
            }
        }

        is Resource.Loading -> {
            CircularProgressIndicator()
        }

        is Resource.Success -> {
            val current = (state.value as Resource.Success<Account>).data
            val edited = editedAccount.value ?: current
            if (showNameDialog.value) {
                AccountNameDialog(
                    initialName = edited.name,
                    onSave = {
                        accountViewModel.updateAccountName(it)
                        showNameDialog.value = false
                    },
                    onDismiss = { showNameDialog.value = false }
                )
            }

            if (showBalanceDialog.value) {
                BalanceDialog(
                    initialBalance = edited.balance.toString(),
                    onSave = {
                        accountViewModel.updateAccountBalance(it)
                        showBalanceDialog.value = false
                    },
                    onDismiss = { showBalanceDialog.value = false }
                )
            }
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                AccountName(
                    edited.name,
                    onClick = { showNameDialog.value = true }
                )
                Balance(
                    "${edited.balance} ${edited.currency}",
                    onClick = { showBalanceDialog.value = true }
                )
            }
        }
    }
}

@Composable
fun AccountName(
    name: String,
    onClick: () -> Unit
) {
    CustomListItem(
        leftTitle = "Название счёта",
        rightTitle = name,
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



