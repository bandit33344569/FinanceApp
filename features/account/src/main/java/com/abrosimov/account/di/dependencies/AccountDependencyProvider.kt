package com.abrosimov.account.di.dependencies

interface AccountDependencyProvider {
    val accountDependencies: AccountDependencies

    companion object : AccountDependencyProvider by AccountDependenciesStore
}