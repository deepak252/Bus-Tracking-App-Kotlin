package com.example.bustrackingapp.presentation.screens.dashboard.profile

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bustrackingapp.common.NetworkResult
import com.example.bustrackingapp.domain.repository.UserPrefsRepository
import com.example.bustrackingapp.domain.repository.UserRepository
import com.example.bustrackingapp.utils.CustomLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userPrefsRepository: UserPrefsRepository
) : ViewModel(){
    private val logger = CustomLogger(c = "ProfileViewModel")
    var uiState by mutableStateOf(ProfileUiState())
        private  set
    init {
        viewModelScope.launch {
            getUser(true)
        }
    }

    fun getUser(isLoading : Boolean = false, isRefreshing : Boolean = false){
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = isLoading, isRefreshing = isRefreshing)
            uiState = try{
                when(val result = userRepository.getUser()){
                    is NetworkResult.Success->{
                        uiState.copy(user = result.data, error = null)
                    }
                    is NetworkResult.Error->{
                        uiState.copy(error = result.message)
                    }
                }
            }catch (e : Exception){
                uiState.copy(error = e.message)
            }
            uiState = uiState.copy(isLoading = false, isRefreshing = false)
            delay(5000)
            uiState = uiState.copy(error = null)
        }

    }

    fun onLogOutClick(){
        viewModelScope.launch {
            userPrefsRepository.updateToken("")
        }
    }

    fun testBlock(){
        logger.info(1)
        runBlocking {
            //main thread
             viewModelScope.launch {
                delay(2000)
                logger.info("2 ${Thread.currentThread().name}")
             }
        }
        logger.info(10)

    }
}