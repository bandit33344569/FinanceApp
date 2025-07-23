package com.abrosimov.transactions.analytics.di

import com.abrosimov.transactions.analytics.ui.viewModel.AnalyticsViewModelFactory
import com.abrosimov.transactions.di.TransactionsDependencies
import dagger.Component

@Component(dependencies = [TransactionsDependencies::class])
internal interface AnalyticsComponent {
    @Component.Builder
    interface Builder {
        fun dependencies(transactionsDependencies: TransactionsDependencies): Builder

        fun build(): AnalyticsComponent
    }

    val analyticsViewModelFactory: AnalyticsViewModelFactory
}