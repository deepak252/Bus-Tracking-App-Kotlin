package com.example.bustrackingapp.feature_bus_routes.presentation.bus_routes

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bustrackingapp.core.util.Resource
import com.example.bustrackingapp.core.util.ValidationUtil
import com.example.bustrackingapp.feature_bus_routes.domain.use_case.BusRouteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BusRoutesViewModel @Inject constructor(
    private val busRouteUseCases: BusRouteUseCases
) : ViewModel(){
    var uiState by mutableStateOf(BusRoutesUiState())
        private set

    init {
        getAllBusRoutes(isLoading = true)
    }

    fun onSearchInputChange(newVal : String){
        uiState = uiState.copy(
            searchInput = newVal
        )
        val searchInput = newVal.trim()
        uiState = if(searchInput.isNotEmpty()){
            uiState.copy(
                busRoutes = uiState.allRoutes.filter {
                    it.routeNo.contains(searchInput, true)
                            || it.name.contains(searchInput, true)
                }
            )
        }else{
            uiState.copy(busRoutes = uiState.allRoutes)
        }

    }

    fun getAllBusRoutes(isLoading : Boolean = false, isRefreshing : Boolean = false){
        if(uiState.isLoading || uiState.isRefreshing){
            return
        }
        busRouteUseCases.getAllBusRoutes().onEach { result->
            uiState = when(result){
                is Resource.Success ->{
                    uiState.copy(
                        allRoutes = result.data?: emptyList(),
                        busRoutes = result.data?: emptyList(),
                        isLoading = false,
                        isRefreshing = false,
                        error = null
                    )
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