package com.obi.moviecompose.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel : ViewModel() {

    private val _events: MutableStateFlow<Event?> = MutableStateFlow(null)
    open val events: StateFlow<Event?> = _events

    suspend fun emitEvent(event: Event) {
        _events.emit(event)
    }

    sealed class Event {
        data class ShowError(val errorMessage: String) : Event()
    }
}