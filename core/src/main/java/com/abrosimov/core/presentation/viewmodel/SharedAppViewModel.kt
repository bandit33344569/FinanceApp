package com.abrosimov.core.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SharedAppViewModel @Inject constructor() : ViewModel() {

    init {
        Log.d("SharedAppViewModel", "Создан инстанс: $this")
    }
    private val _saveEvent = MutableSharedFlow<Unit>()
    val saveEvent: SharedFlow<Unit> = _saveEvent

    private val _discardEvent = MutableSharedFlow<Unit>()
    val discardEvent: SharedFlow<Unit> = _discardEvent


    fun onSaveClicked() {
        viewModelScope.launch {
            _saveEvent.emit(Unit)
        }
    }

    fun onDiscardClicked() {
        viewModelScope.launch {
            _discardEvent.emit(Unit)
        }
    }
}