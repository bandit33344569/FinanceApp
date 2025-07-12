package com.abrosimov.transactions.edit_transaction.ui.state

import com.abrosimov.impl.models.Category

data class TransactionFormState(
    val id: Int? = null,
    val amount: String = "",
    val comment: String? = null,
    val date: String = "",
    val time: String = "",
    val category: Category? = null,
)