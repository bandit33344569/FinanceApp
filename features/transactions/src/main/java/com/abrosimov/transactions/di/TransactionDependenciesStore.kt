package com.abrosimov.transactions.di

import kotlin.properties.Delegates.notNull

object TransactionDependenciesStore : TransactionDependencyProvider {
    override var transactionsDependencies: TransactionsDependencies by notNull()
}