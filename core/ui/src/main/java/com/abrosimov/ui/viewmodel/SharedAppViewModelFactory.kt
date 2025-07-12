package com.abrosimov.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class SharedAppViewModelFactory @Inject constructor(
    private val viewModel: SharedAppViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == SharedAppViewModel::class.java)
        return viewModel as T
    }
}