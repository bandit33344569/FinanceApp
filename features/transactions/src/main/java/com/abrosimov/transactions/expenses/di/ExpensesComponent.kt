package com.abrosimov.transactions.expenses.di

import com.abrosimov.transactions.di.TransactionsDependencies
import com.abrosimov.transactions.expenses.ui.ExpensesViewModelFactory
import dagger.Component

@Component(dependencies = [TransactionsDependencies::class])
internal interface ExpensesComponent {
    @Component.Builder
    interface Builder {
        fun dependencies(transactionsDependencies: TransactionsDependencies): Builder

        fun build(): ExpensesComponent
    }

    val expensesViewModelFactory: ExpensesViewModelFactory
}