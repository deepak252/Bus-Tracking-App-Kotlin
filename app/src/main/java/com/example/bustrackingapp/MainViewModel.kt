package com.example.bustrackingapp

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bustrackingapp.core.domain.repository.UserPrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPrefsRepository: UserPrefsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){
    private val tokenKey = "token"
    private val userTypeKey = "userType"
    private val loadingKey = "loading"

    val token = savedStateHandle.getStateFlow(tokenKey,"")
    val userType = savedStateHandle.getStateFlow(userTypeKey,"")
    val loading = savedStateHandle.getStateFlow(loadingKey,false)

    init {
        viewModelScope.launch {
            userPrefsRepository.getUserType.collect{
                savedStateHandle[loadingKey] = true;
                savedStateHandle[userTypeKey] = it
                savedStateHandle[loadingKey] = false;
            }
        }
        viewModelScope.launch {
            savedStateHandle[loadingKey] = true;
            userPrefsRepository.getToken.collect{
                savedStateHandle[loadingKey] = true;
                savedStateHandle[tokenKey] = it
                if(it.isNotEmpty()){ // To Show Splash if user logged in
                    delay(1000)
                }
                savedStateHandle[loadingKey] = false;
            }
            savedStateHandle[loadingKey] = false;
        }
    }


}