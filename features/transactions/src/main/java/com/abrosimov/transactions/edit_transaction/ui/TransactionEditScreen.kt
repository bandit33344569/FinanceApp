package com.abrosimov.transactions.edit_transaction.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abrosimov.transactions.di.TransactionDependenciesStore
import com.abrosimov.transactions.edit_transaction.di.DaggerEditTransactionComponent
import com.abrosimov.transactions.edit_transaction.ui.dialogs.AmountInputDialog
import com.abrosimov.transactions.edit_transaction.ui.dialogs.CategorySelectionDialog
import com.abrosimov.transactions.edit_transaction.ui.dialogs.CommentInputDialog
import com.abrosimov.transactions.edit_transaction.ui.dialogs.TimePickerDialog
import com.abrosimov.transactions.edit_transaction.ui.items.CategoryEditItem
import com.abrosimov.transactions.edit_transaction.ui.items.CommentEditItem
import com.abrosimov.transactions.edit_transaction.ui.items.DataEditItem
import com.abrosimov.transactions.edit_transaction.ui.items.SumEditItem
import com.abrosimov.transactions.edit_transaction.ui.items.TimeEditItem
import com.abrosimov.transactions.edit_transaction.ui.state.TransactionLoadState
import com.abrosimov.transactions.edit_transaction.ui.viewmodel.TransactionEditViewModel
import com.abrosimov.ui.composableFunctions.DatePickerModal
import com.abrosimov.ui.viewmodel.SharedAppViewModel
import java.util.Date
import androidx.compose.material3.CircularProgressIndicator

@Composable
fun TransactionEditScreen(
    id: Int?,
) {
    val editTransactionsComponent = remember {
        DaggerEditTransactionComponent
            .builder()
            .dependencies(transactionsDependencies = TransactionDependenciesStore.transactionsDependencies)
            .build()
    }

    val editTransactionViewModel =
        viewModel<TransactionEditViewModel>(factory = editTransactionsComponent.TransactionEditViewModelFactory)
    val sharedAppViewModel =
        viewModel<SharedAppViewModel>(factory = editTransactionsComponent.sharedAppViewModelFactory())

    val loadState = editTransactionViewModel.transactionState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        editTransactionViewModel.loadTransaction(id)
    }

    var showCategoryDialog = remember { mutableStateOf(false) }
    var showSumDialog = remember { mutableStateOf(false) }
    var showDatePicker = remember { mutableStateOf(false) }
    var showTimePicker = remember { mutableStateOf(false) }
    var showCommentDialog = remember { mutableStateOf(false) }

    when (val state = loadState.value) {
        is TransactionLoadState.Loading -> {
            CircularProgressIndicator(modifier = Modifier)
        }

        is TransactionLoadState.Error -> {
            Text(
                text = "Ошибка загрузки данных: ${state.message.orEmpty()}",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }

        is TransactionLoadState.Success -> {
            val formState = state.state

            Column(modifier = Modifier.fillMaxSize()) {
                CategoryEditItem(
                    category = formState.category,
                    onClick = { showCategoryDialog.value = true }
                )

                SumEditItem(
                    sum = formState.amount,
                    onClick = { showSumDialog.value = true }
                )

                DataEditItem(
                    date = formState.date,
                    onClick = { showDatePicker.value = true }
                )

                TimeEditItem(
                    time = formState.time,
                    onClick = { showTimePicker.value = true }
                )

                CommentEditItem(
                    comment = formState.comment,
                    onClick = { showCommentDialog.value = true }
                )
            }

            if (showCommentDialog.value) {
                CommentInputDialog(
                    initialComment = formState.comment,
                    onSave = {
                        editTransactionViewModel.setComment(it)
                        showCommentDialog.value = false
                    },
                    onDismiss = { showCommentDialog.value = false }
                )
            }

            if (showCategoryDialog.value) {
                CategorySelectionDialog(
                    onCategorySelected = {
                        editTransactionViewModel.setCategory(it)
                        showCategoryDialog.value = false
                    },
                    onDismiss = { showCategoryDialog.value = false },
                    viewModel = editTransactionViewModel
                )
            }

            if (showSumDialog.value) {
                AmountInputDialog(
                    onSave = {
                        editTransactionViewModel.setAmount(it)
                        showSumDialog.value = false
                    },
                    onDismiss = { showSumDialog.value = false },
                    initialAmount = formState.amount
                )
            }

            if (showDatePicker.value) {
                DatePickerModal(
                    onDateSelected = { editTransactionViewModel.setDate(Date(it ?: 0)) },
                    onDismiss = { showDatePicker.value = false }
                )
            }

            if (showTimePicker.value) {
                TimePickerDialog(
                    initialTime = formState.time,
                    onTimeSelected = { editTransactionViewModel.setTime(it) },
                    onDismiss = { showTimePicker.value = false }
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        sharedAppViewModel.saveEvent.collect { event ->
            editTransactionViewModel.saveTransaction()
        }
    }
}
