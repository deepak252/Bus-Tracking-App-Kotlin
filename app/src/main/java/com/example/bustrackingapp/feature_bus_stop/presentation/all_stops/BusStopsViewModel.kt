package com.example.bustrackingapp.feature_bus_stop.presentation.all_stops

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bustrackingapp.core.util.Resource
import com.example.bustrackingapp.feature_bus_routes.domain.use_case.BusRouteUseCases
import com.example.bustrackingapp.feature_bus_routes.presentation.bus_routes.BusRoutesUiState
import com.example.bustrackingapp.feature_bus_stop.domain.use_case.GetAllBusStopsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class BusStopsViewModel @Inject constructor(
    private val busStopsUseCase: GetAllBusStopsUseCase
) : ViewModel(){
    var uiState by mutableStateOf(BusStopsUiState())
        private set

    init {
        getAllBusStops(isLoading = true)
    }

    fun getAllBusStops(isLoading : Boolean = false, isRefreshing : Boolean = false){
        if(uiState.isLoading || uiState.isRefreshing){
            return
        }
        busStopsUseCase().onEach { result->
            uiState = when(result){
                is Resource.Success ->{
                    uiState.copy(busStops = result.data?: emptyList(), isLoading = false, isRefreshing = false, error = null)
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