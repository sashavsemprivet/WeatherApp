package com.example.weatherapp.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel : ViewModel() {

    abstract val state: StateFlow<State>

    open fun doAction(action: Action) = Unit
}