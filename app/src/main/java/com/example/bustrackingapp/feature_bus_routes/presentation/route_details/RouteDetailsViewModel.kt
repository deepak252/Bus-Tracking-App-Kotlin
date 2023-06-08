package com.example.bustrackingapp.feature_bus_routes.presentation.route_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bustrackingapp.core.util.Resource
import com.example.bustrackingapp.feature_bus.domain.use_cases.GetBusesForRouteUseCase
import com.example.bustrackingapp.feature_bus_routes.domain.use_case.GetBusRouteUseCase
import com.example.bustrackingapp.feature_bus_routes.presentation.bus_routes.BusRoutesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RouteDetailsViewModel @Inject constructor(
    private val getBusRouteUseCase: GetBusRouteUseCase,
    private val getBusesForRouteUseCase: GetBusesForRouteUseCase
) : ViewModel(){
    var uiState by mutableStateOf(RouteDetailsUiState())
        private set

    init {
//        viewModelScope.launch {
//            getBusRouteDetails("910_UP",isLoading = true)
//        }
    }

    fun getBusRouteDetails(routeNo : String, isLoading : Boolean = false, isRefreshing : Boolean = false){
        if(uiState.isLoading || uiState.isRefreshing){
            return
        }
        getBusRouteUseCase(routeNo).onEach { result->
            uiState = when(result){
                is Resource.Success ->{
                    uiState.copy(route = result.data, isLoading = false, isRefreshing = false, error = null)
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