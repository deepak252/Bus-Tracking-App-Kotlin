package com.example.bustrackingapp.feature_bus.di

import com.example.bustrackingapp.feature_bus.data.remote.api.BusApiService
import com.example.bustrackingapp.feature_bus.data.remote.repository.BusRepositoryImpl
import com.example.bustrackingapp.feature_bus.domain.repository.BusRepository
import com.example.bustrackingapp.feature_bus.domain.use_cases.BusUseCases
import com.example.bustrackingapp.feature_bus.domain.use_cases.GetAllBusesUseCase
import com.example.bustrackingapp.feature_bus.domain.use_cases.GetBusByVehNoUseCase
import com.example.bustrackingapp.feature_bus.domain.use_cases.GetBusesForRouteUseCase
import com.example.bustrackingapp.feature_bus.domain.use_cases.GetBusesForStopUseCase
import com.example.bustrackingapp.feature_bus.domain.use_cases.GetNearbyBusesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BusStopModule {

    @Provides
    @Singleton
    fun provideBusApiService(
        retrofit: Retrofit
    ) : BusApiService = retrofit.create(BusApiService::class.java)

    @Provides
    @Singleton
    fun provideBusRepository(
        busApiService: BusApiService,
        dispatcher : CoroutineDispatcher
    ) : BusRepository = BusRepositoryImpl( busApiService, dispatcher)

    @Provides
    @Singleton
    fun provideBusUseCases(
        getBusByVehNoUseCase: GetBusByVehNoUseCase,
        getAllBusesUseCase: GetAllBusesUseCase,
        getBusesForRouteUseCase: GetBusesForRouteUseCase,
        getBusesForStopUseCase: GetBusesForStopUseCase,
        getNearbyBusesUseCase: GetNearbyBusesUseCase
    ) : BusUseCases = BusUseCases(
        getAllBuses = getAllBusesUseCase,
        getBusByVehNo = getBusByVehNoUseCase,
        getBusesForRoute = getBusesForRouteUseCase,
        getBusesForStop = getBusesForStopUseCase,
        getNearbyBuses = getNearbyBusesUseCase
    )

}