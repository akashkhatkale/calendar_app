package com.demo.googlecalendar.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.googlecalendar.domain.usecase.AddEventUseCase
import com.demo.googlecalendar.presentation.AddEvent
import com.demo.googlecalendar.presentation.utils.UIState
import com.google.api.client.util.DateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val addEventUseCase: AddEventUseCase
): ViewModel() {

    private val _state: MutableStateFlow<UIState<Boolean>> = MutableStateFlow(UIState.Loading())
    val state: StateFlow<UIState<Boolean>> = _state.asStateFlow()

    fun addEvent(
        summary: String,
        description: String,
        startTime: DateTime,
        endTime: DateTime,
    ) = viewModelScope.launch(Dispatchers.IO) {
        _state.emit(UIState.Loading())
        addEventUseCase.invoke(
            AddEventUseCase.AddEventUseCaseInput(
                summary, description, startTime, endTime
            )
        ).onFailure {
            _state.emit(UIState.Error(it))
        }.onSuccess {
            _state.emit(UIState.Success(it))
        }
    }

}