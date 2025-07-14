package com.abrosimov.categories.di.dependencies

interface CategoriesDependencyProvider{
    val categoriesDependencies: CategoriesDependencies

    companion object : CategoriesDependencyProvider by CategoriesDependenciesStore
}