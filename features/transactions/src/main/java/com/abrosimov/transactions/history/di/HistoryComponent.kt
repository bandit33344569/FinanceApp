package com.abrosimov.transactions.history.di

import com.abrosimov.transactions.di.TransactionsDependencies
import com.abrosimov.transactions.history.ui.HistoryViewModelFactory
import dagger.Component

@Component(dependencies = [TransactionsDependencies::class])
internal interface HistoryComponent {
    @Component.Builder
    interface Builder {
        fun dependencies(transactionsDependencies: TransactionsDependencies): Builder

        fun build(): HistoryComponent
    }

    val historyViewModelFactory: HistoryViewModelFactory
}