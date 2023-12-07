package com.example.bustrackingapp.feature_bus_stop.di

import com.example.bustrackingapp.feature_bus_stop.data.api.BusStopApiService
import com.example.bustrackingapp.feature_bus_stop.data.repository.BusStopRepositoryImpl
import com.example.bustrackingapp.feature_bus_stop.domain.repository.BusStopRepository
import com.example.bustrackingapp.feature_bus_stop.domain.use_case.BusStopUseCases
import com.example.bustrackingapp.feature_bus_stop.domain.use_case.GetAllBusStopsUseCase
import com.example.bustrackingapp.feature_bus_stop.domain.use_case.GetBusStopUseCase
import com.example.bustrackingapp.feature_bus_stop.domain.use_case.GetNearbyBusStopsUseCase
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
    fun provideBusStopApiService(
        retrofit: Retrofit
    ) : BusStopApiService = retrofit.create(BusStopApiService::class.java)

    @Provides
    @Singleton
    fun provideBusStopRepository(
        stopApiService: BusStopApiService,
        dispatcher : CoroutineDispatcher
    ) : BusStopRepository = BusStopRepositoryImpl(stopApiService, dispatcher)

    @Provides
    @Singleton
    fun provideBusStopUseCases(
        getAllBusStopsUseCase: GetAllBusStopsUseCase,
        getNearbyBusStopsUseCase: GetNearbyBusStopsUseCase,
        getBusStopUseCase: GetBusStopUseCase,
    ) : BusStopUseCases = BusStopUseCases(
        getAllBusStops = getAllBusStopsUseCase,
        getNearbyBusStops = getNearbyBusStopsUseCase,
        getBusStop = getBusStopUseCase,
    )

}