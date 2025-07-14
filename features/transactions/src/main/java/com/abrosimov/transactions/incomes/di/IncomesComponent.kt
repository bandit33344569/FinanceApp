package com.abrosimov.transactions.incomes.di

import com.abrosimov.transactions.di.TransactionsDependencies
import com.abrosimov.transactions.incomes.ui.IncomesViewModelFactory
import dagger.Component

@Component(dependencies = [TransactionsDependencies::class])
internal interface IncomesComponent {
    @Component.Builder
    interface Builder {
        fun dependencies(transactionsDependencies: TransactionsDependencies): Builder

        fun build(): IncomesComponent
    }

    val incomesViewModelFactory: IncomesViewModelFactory
}