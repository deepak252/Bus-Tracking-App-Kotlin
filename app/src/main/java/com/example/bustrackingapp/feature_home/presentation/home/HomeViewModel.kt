package com.example.bustrackingapp.feature_home.presentation.home

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bustrackingapp.core.domain.repository.LocationRepository
import com.example.bustrackingapp.core.util.LoggerUtil
import com.example.bustrackingapp.core.util.Resource
import com.example.bustrackingapp.feature_bus.domain.use_cases.BusUseCases
import com.example.bustrackingapp.feature_bus_stop.domain.use_case.BusStopUseCases
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val busStopUseCases: BusStopUseCases,
    private val busUseCases: BusUseCases,
    private val locationRepository: LocationRepository,
): ViewModel(){
    private val logger = LoggerUtil(c= "HomeVieModel")
    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        getLocationBusesStops()
    }

    fun getLocationBusesStops(){
        if( uiState.isLoadingLocation){
            return
        }
        uiState = uiState.copy(isLoadingLocation = true)
        locationRepository.getLocation(
            onSuccess = {
                uiState = uiState.copy(location = it, errorLocation = null)
                getNearbyBuses(isLoading = true)
                getNearbyStops(isLoading = true)
            },
            onError = {
                uiState = uiState.copy(errorLocation = it.message)
            }
        )
        uiState = uiState.copy(isLoadingLocation = false)
    }

    private fun getAllBusStops(isLoading : Boolean = false, isRefreshing : Boolean = false ){
        if( uiState.isLoadingLocation || uiState.isLoadingNearbyStops || uiState.isRefreshingNearbyStops){
            return
        }
        busStopUseCases.getAllBusStops().onEach { result->
            uiState = when(result){
                is Resource.Success ->{
                    uiState.copy(
                        nearbyBusStops = result.data?: emptyList(),
                        isLoadingNearbyStops = false,
                        isRefreshingNearbyStops = false,
                        errorNearbyBuses = null,
                    )
                }
                is Resource.Error ->{
                    uiState.copy(
                        errorNearbyBuses = result.message,
                        isLoadingNearbyStops = false,
                        isRefreshingNearbyStops = false,
                    )
                }
                is Resource.Loading ->{
                    uiState.copy(
                        errorNearbyBuses = null,
                        isLoadingNearbyStops = isLoading,
                        isRefreshingNearbyStops = isRefreshing,
                    )
                }
            }
        }
            .launchIn(viewModelScope)
    }

    fun getNearbyStops(isLoading : Boolean = false, isRefreshing : Boolean = false ){
        if( uiState.isLoadingLocation || uiState.isLoadingNearbyStops || uiState.isRefreshingNearbyStops){
            return
        }
        if(uiState.location==null){
            uiState = uiState.copy(errorNearbyStops = "Couldn't fetch current location")
            return
        }
        busStopUseCases.getNearbyBusStops(
            uiState.location!!.latitude,
            uiState.location!!.longitude
        ).onEach { result->
            uiState = when(result){
                is Resource.Success ->{
                    uiState.copy(
                        nearbyBusStops = result.data?: emptyList(),
                        isLoadingNearbyStops = false,
                        isRefreshingNearbyStops = false,
                        errorNearbyBuses = null,
                    )
                }
                is Resource.Error ->{
                    uiState.copy(
                        errorNearbyBuses = result.message,
                        isLoadingNearbyStops = false,
                        isRefreshingNearbyStops = false,
                    )
                }
                is Resource.Loading ->{
                    uiState.copy(
                        errorNearbyBuses = null,
                        isLoadingNearbyStops = isLoading,
                        isRefreshingNearbyStops = isRefreshing,
                    )
                }
            }
        }
            .launchIn(viewModelScope)
    }

    fun getNearbyBuses(isLoading : Boolean = false, isRefreshing : Boolean = false ){
        if(uiState.isLoadingNearbyBuses || uiState.isRefreshingNearbyBuses){
            return
        }
        busUseCases.getAllBuses().onEach { result->
            uiState = when(result){
                is Resource.Success ->{
                    uiState.copy(
                        nearbyBuses = result.data?: emptyList(),
                        isLoadingNearbyBuses = false,
                        isRefreshingNearbyBuses = false,
                        errorNearbyBuses = null,
                    )
                }
                is Resource.Error ->{
                    uiState.copy(
                        errorNearbyBuses = result.message,
                        isLoadingNearbyBuses = false,
                        isRefreshingNearbyBuses = false,
                    )
                }
                is Resource.Loading ->{
                    uiState.copy(
                        errorNearbyBuses = null,
                        isLoadingNearbyBuses = isLoading,
                        isRefreshingNearbyBuses = isRefreshing,
                    )
                }
            }
        }
            .launchIn(viewModelScope)
    }
}

//    private val _uiState = MutableHomeUiState()
//    val uiState: HomeUiState = _uiState
//
//    init {
//        getNearbyBuses(isLoading = true)
//        getNearbyStops(isLoading = true)
//    }
//
//    fun getNearbyStops(isLoading : Boolean = false, isRefreshing : Boolean = false ){
//        if(uiState.isLoadingNearbyStops || uiState.isRefreshingNearbyStops){
//            return
//        }
//        busStopUseCases.getAllBusStops().onEach { result->
//            when(result){
//                is Resource.Success ->{
//                    _uiState.apply {
//                        nearbyBusStops = result.data?: emptyList()
//                        isLoadingNearbyStops = false
//                        isRefreshingNearbyStops = false
//                        errorNearbyBuses = null
//                    }
//                }
//                is Resource.Error ->{
//                    _uiState.apply{
//                        errorNearbyBuses = result.message
//                        isLoadingNearbyStops = false
//                        isRefreshingNearbyStops = false
//                    }
//                }
//                is Resource.Loading ->{
//                    _uiState.apply{
//                        errorNearbyBuses = null
//                        isLoadingNearbyStops = isLoading
//                        isRefreshingNearbyStops = isRefreshing
//                    }
//                }
//            }
//        }
//            .launchIn(viewModelScope)
//    }
//
//    fun getNearbyBuses(isLoading : Boolean = false, isRefreshing : Boolean = false ){
//        if(uiState.isLoadingNearbyBuses || uiState.isRefreshingNearbyBuses){
//            return
//        }
//        busUseCases.getAllBuses().onEach { result->
//            when(result){
//                is Resource.Success ->{
//                    _uiState.apply {
//                        nearbyBuses = result.data ?: emptyList()
//                        isLoadingNearbyBuses = false
//                        isRefreshingNearbyBuses = false
//                        errorNearbyBuses = null
//                    }
//                }
//                is Resource.Error ->{
//                    _uiState.apply {
//                        errorNearbyBuses = result.message
//                        isLoadingNearbyBuses = false
//                        isRefreshingNearbyBuses = false
//                    }
//                }
//                is Resource.Loading ->{
//                    _uiState.apply {
//                        errorNearbyBuses = null
//                        isLoadingNearbyBuses = isLoading
//                        isRefreshingNearbyBuses = isRefreshing
//                    }
//                }
//            }
//        }
//            .launchIn(viewModelScope)
//    }
