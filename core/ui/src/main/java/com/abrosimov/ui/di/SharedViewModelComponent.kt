package com.abrosimov.ui.di

import com.abrosimov.ui.viewmodel.SharedAppViewModelFactory
import dagger.Component

@Component
interface SharedViewModelComponent {
    @Component.Builder
    interface Builder {
        fun build(): SharedViewModelComponent
    }

    val sharedAppViewModelFactory: SharedAppViewModelFactory
}