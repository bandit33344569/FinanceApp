package com.abrosimov.transactions.di

interface TransactionDependencyProvider {
    val transactionsDependencies: TransactionsDependencies

    companion object : TransactionDependencyProvider by TransactionDependenciesStore
}