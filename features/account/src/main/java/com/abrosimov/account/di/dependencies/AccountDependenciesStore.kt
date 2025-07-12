package com.abrosimov.account.di.dependencies

import kotlin.properties.Delegates.notNull

object AccountDependenciesStore : AccountDependencyProvider {
    override var accountDependencies: AccountDependencies by notNull()
}
