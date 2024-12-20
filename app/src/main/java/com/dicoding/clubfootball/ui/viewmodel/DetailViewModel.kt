package com.dicoding.clubfootball.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.clubfootball.model.Football
import com.dicoding.clubfootball.repository.FootballRepository
import com.dicoding.clubfootball.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: FootballRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Football>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Football>>
        get() = _uiState

    fun getNewsById(id: Int) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(repository.getNewsById(id))
    }

    fun updateNews(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateNews(id, !newState)
            .collect{ isUpdated ->
                if(isUpdated) getNewsById(id)
            }
    }
}