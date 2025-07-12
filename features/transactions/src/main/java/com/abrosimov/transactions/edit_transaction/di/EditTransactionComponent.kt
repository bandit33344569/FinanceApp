package com.abrosimov.transactions.edit_transaction.di

import com.abrosimov.transactions.di.TransactionsDependencies
import com.abrosimov.transactions.edit_transaction.ui.viewmodel.TransactionEditViewModelFactory
import com.abrosimov.ui.viewmodel.SharedAppViewModelFactory
import dagger.Component

@Component(dependencies = [TransactionsDependencies::class])
internal interface EditTransactionComponent {
    @Component.Builder
    interface Builder {
        fun dependencies(transactionsDependencies: TransactionsDependencies): Builder

        fun build(): EditTransactionComponent
    }
    fun sharedAppViewModelFactory(): SharedAppViewModelFactory
    val TransactionEditViewModelFactory: TransactionEditViewModelFactory
}