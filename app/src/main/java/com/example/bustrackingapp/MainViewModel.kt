package com.example.bustrackingapp

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bustrackingapp.domain.repository.UserPrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPrefsRepository: UserPrefsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){
    private val tokenKey = "token"
    private val loadingKey = "loading"

    val token = savedStateHandle.getStateFlow(tokenKey,"")
    val loading = savedStateHandle.getStateFlow(loadingKey,false)

    init {
        viewModelScope.launch {
            savedStateHandle[loadingKey] = true;
            userPrefsRepository.getToken.collect{
                savedStateHandle[tokenKey] = it
            }
            delay(3000)
            savedStateHandle[loadingKey] = false;
        }
    }

//    val getToken : StateFlow<String> = userPrefsRepository.getToken.stateIn(
//        viewModelScope,
//        SharingStarted.Lazily,
//        ""
//    )


}