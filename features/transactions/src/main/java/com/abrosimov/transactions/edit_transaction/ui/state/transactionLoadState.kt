package com.abrosimov.transactions.edit_transaction.ui.state

sealed interface TransactionLoadState {
    object Loading : TransactionLoadState
    data class Success(val state: TransactionFormState) : TransactionLoadState
    data class Error(val message: String? = null) : TransactionLoadState
}