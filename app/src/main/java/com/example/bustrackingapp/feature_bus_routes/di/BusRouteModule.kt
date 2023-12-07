package com.example.bustrackingapp.feature_bus_routes.di

import com.example.bustrackingapp.feature_bus_routes.data.remote.api.BusRouteApiService
import com.example.bustrackingapp.feature_bus_routes.data.repository.BusRouteRepositoryImpl
import com.example.bustrackingapp.feature_bus_routes.domain.repository.BusRouteRepository
import com.example.bustrackingapp.feature_bus_routes.domain.use_case.BusRouteUseCases
import com.example.bustrackingapp.feature_bus_routes.domain.use_case.GetAllBusRoutesUseCase
import com.example.bustrackingapp.feature_bus_routes.domain.use_case.GetBusRouteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object BusRouteModule {

    @Provides
    @Singleton
    fun provideBusRouteApiService(
        retrofit: Retrofit
    ) : BusRouteApiService = retrofit.create(BusRouteApiService::class.java)

    @Provides
    @Singleton
    fun provideBusRouteRepository(
        routeApiService: BusRouteApiService,
        dispatcher : CoroutineDispatcher
    ) : BusRouteRepository = BusRouteRepositoryImpl(routeApiService, dispatcher)

    @Provides
    @Singleton
    fun provideBusRouteUseCases(
        getAllBusRoutesUseCase: GetAllBusRoutesUseCase,
        getBusRouteUseCase : GetBusRouteUseCase
    ) : BusRouteUseCases = BusRouteUseCases(
        getAllBusRoutes = getAllBusRoutesUseCase,
        getBusRoute = getBusRouteUseCase
    )

}