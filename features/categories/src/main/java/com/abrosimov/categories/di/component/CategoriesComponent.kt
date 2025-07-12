package com.abrosimov.categories.di.component

import com.abrosimov.categories.di.dependencies.CategoriesDependencies
import com.abrosimov.categories.presentation.CategoriesViewModelFactory
import dagger.Component

@Component(dependencies = [CategoriesDependencies::class])
internal interface CategoriesComponent {
    @Component.Builder
    interface Builder {
        fun dependencies(categoriesDependencies: CategoriesDependencies): Builder

        fun build(): CategoriesComponent
    }

    val categoriesViewModelFactory: CategoriesViewModelFactory
}