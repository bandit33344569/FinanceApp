package com.abrosimov.account.di.components

import com.abrosimov.account.di.dependencies.AccountDependencies
import com.abrosimov.account.presentation.viewmodel.AccountViewModelFactory
import com.abrosimov.ui.viewmodel.SharedAppViewModelFactory
import dagger.Component

@Component(dependencies = [AccountDependencies::class])
internal interface AccountComponent {
    @Component.Builder
    interface Builder {
        fun dependencies(accountDependencies: AccountDependencies): Builder

        fun build(): AccountComponent
    }
    fun sharedAppViewModelFactory(): SharedAppViewModelFactory
    val accountViewModelFactory: AccountViewModelFactory
}