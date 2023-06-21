package com.example.bustrackingapp.feature_profile.presentation.profile

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bustrackingapp.core.domain.repository.UserPrefsRepository
import com.example.bustrackingapp.core.util.LoggerUtil
import com.example.bustrackingapp.core.util.Resource
import com.example.bustrackingapp.feature_profile.domain.use_case.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    private val userPrefsRepository: UserPrefsRepository
) : ViewModel(){
    private val logger = LoggerUtil(c = "ProfileViewModel")
    var uiState by mutableStateOf(ProfileUiState())
        private  set
    init {
        getUser(isLoading = true)
    }

    fun getUser(isLoading : Boolean = false, isRefreshing : Boolean = false){
        if(uiState.isLoading || uiState.isRefreshing){
            return
        }
        profileUseCases.getProfile().onEach { result->
            uiState = when (result) {
                is Resource.Success -> {
                    uiState.copy( user = result.data ,isLoading = false, isRefreshing = false, error = null,)
                }
                is Resource.Error -> {
                    uiState.copy( error = result.message,isLoading = false, isRefreshing = false)
                }
                is Resource.Loading -> {
                    uiState.copy(isLoading = isLoading, isRefreshing = isRefreshing, error = null, )
                }
            }
        }
//            .onCompletion {
//                delay(5000)
//                uiState = uiState.copy(error = null)
//            }
        .launchIn(viewModelScope)
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