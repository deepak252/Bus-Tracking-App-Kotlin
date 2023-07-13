package com.example.bustrackingapp.feature_home.presentation.home_driver

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bustrackingapp.core.domain.repository.LocationRepository
import com.example.bustrackingapp.core.util.LoggerUtil
import com.example.bustrackingapp.feature_bus.domain.use_cases.GetNearbyBusesUseCase
import com.example.bustrackingapp.feature_bus_stop.domain.use_case.BusStopUseCases
import com.example.bustrackingapp.feature_home.presentation.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeDriverViewModel @Inject constructor(
    private val busStopUseCases: BusStopUseCases,
    private val nearbyBusesUseCase: GetNearbyBusesUseCase,
    private val locationRepository: LocationRepository,
): ViewModel() {
    private val logger = LoggerUtil(c = "HomeDriverViewModel")
    var uiState by mutableStateOf(HomeDriverUiState())
        private set

    init {
    }



}