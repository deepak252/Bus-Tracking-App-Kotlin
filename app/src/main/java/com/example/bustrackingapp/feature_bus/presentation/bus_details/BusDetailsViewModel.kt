package com.example.bustrackingapp.feature_bus.presentation.bus_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bustrackingapp.core.util.Resource
import com.example.bustrackingapp.feature_bus.domain.use_cases.GetBusByVehNoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class BusDetailsViewModel @Inject constructor(
    private val getBusByVehNoUseCase: GetBusByVehNoUseCase
) : ViewModel(){
    var uiState by mutableStateOf(BusDetailsUiState())
        private set

    init {
//        viewModelScope.launch {
//            getBusRouteDetails("910_UP",isLoading = true)
//        }
    }

    fun getBusDetails(vehNo : String, isLoading : Boolean = false, isRefreshing : Boolean = false){
        if(uiState.isLoading || uiState.isRefreshing){
            return
        }
        getBusByVehNoUseCase(vehNo).onEach { result->
            uiState = when(result){
                is Resource.Success ->{
                    uiState.copy(bus = result.data, isLoading = false, isRefreshing = false, error = null)
                }
                is Resource.Error ->{
                    uiState.copy(error = result.message, isLoading = false, isRefreshing = false)
                }
                is Resource.Loading ->{
                    uiState.copy(isLoading = isLoading, isRefreshing = isRefreshing, error = null)
                }
            }
        }
            .launchIn(viewModelScope)
    }
}