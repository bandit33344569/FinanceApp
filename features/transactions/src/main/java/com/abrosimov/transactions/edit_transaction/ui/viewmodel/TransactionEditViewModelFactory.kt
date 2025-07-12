package com.abrosimov.transactions.edit_transaction.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abrosimov.impl.repository.AccountDetailsRepository
import com.abrosimov.transactions.edit_transaction.domain.CreateTransactionUseCase
import com.abrosimov.transactions.edit_transaction.domain.GetCategoriesUseCase
import com.abrosimov.transactions.edit_transaction.domain.GetTransactionByIdUseCase
import com.abrosimov.transactions.edit_transaction.domain.UpdateTransactionUseCase
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
internal class TransactionEditViewModelFactory @Inject constructor(
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val accountDetailsRepository: AccountDetailsRepository,
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        TransactionEditViewModel(
            accountDetailsRepository = accountDetailsRepository,
            createTransactionUseCase = createTransactionUseCase,
            updateTransactionUseCase = updateTransactionUseCase,
            getTransactionByIdUseCase = getTransactionByIdUseCase,
            getCategoriesUseCase = getCategoriesUseCase,

        ) as T
}