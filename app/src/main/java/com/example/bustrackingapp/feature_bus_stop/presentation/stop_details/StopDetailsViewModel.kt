package com.example.bustrackingapp.feature_bus_stop.presentation.stop_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bustrackingapp.core.util.Resource
import com.example.bustrackingapp.feature_bus.domain.use_cases.GetBusesForStopUseCase
import com.example.bustrackingapp.feature_bus_stop.domain.use_case.GetBusStopUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class StopDetailsViewModel @Inject constructor(
    private val getBusStopUseCase: GetBusStopUseCase,
    private val getBusesForStopUseCase: GetBusesForStopUseCase
) : ViewModel(){
    var uiState by mutableStateOf(StopDetailsUiState())
        private set

    init {
//        viewModelScope.launch {
//            getBusRouteDetails("910_UP",isLoading = true)
//        }
    }

    fun getBusStopDetails(stopNo : String, isLoading : Boolean = false, isRefreshing : Boolean = false){
        if(uiState.isLoading || uiState.isRefreshing){
            return
        }
        getBusStopUseCase(stopNo).onEach { result->
            uiState = when(result){
                is Resource.Success ->{
                    uiState.copy(stop = result.data, isLoading = false, isRefreshing = false, error = null)
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