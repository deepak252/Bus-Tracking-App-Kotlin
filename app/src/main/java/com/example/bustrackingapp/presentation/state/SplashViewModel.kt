package com.example.bustrackingapp.presentation.state

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bustrackingapp.domain.repository.BusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val busRepository: BusRepository
) : ViewModel(){

    fun fetchBus(){
        viewModelScope.launch {
            val bus = busRepository.getBusByVehNo("DL10")
//            Log.d("MyTag","${bus}")
        }
    }

}