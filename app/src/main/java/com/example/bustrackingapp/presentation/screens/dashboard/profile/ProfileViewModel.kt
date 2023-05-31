package com.example.bustrackingapp.presentation.screens.dashboard.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bustrackingapp.common.NetworkResult
import com.example.bustrackingapp.domain.repository.UserPrefsRepository
import com.example.bustrackingapp.domain.repository.UserRepository
import com.example.bustrackingapp.utils.CustomLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userPrefsRepository: UserPrefsRepository
) : ViewModel(){
    private val logger = CustomLogger(c = "ProfileViewModel")

    fun onLogOutClick(){
        viewModelScope.launch {
            userPrefsRepository.updateToken("")
        }
    }

    fun getUserProfile(){
        viewModelScope.launch {
            val result = userRepository.getUser()

            when(result){
                is NetworkResult.Success->{
                    logger.info("getUserProfile, ${result.data}")
                }
                is NetworkResult.Error ->{
                    logger.error("getUserProfile, ${result.message}")
                }
            }
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