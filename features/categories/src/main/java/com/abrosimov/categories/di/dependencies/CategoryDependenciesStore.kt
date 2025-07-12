package com.abrosimov.categories.di.dependencies

import kotlin.properties.Delegates.notNull

object CategoriesDependenciesStore : CategoriesDependencyProvider {
    override var categoriesDependencies: CategoriesDependencies by notNull()
}