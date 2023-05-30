package com.example.bustrackingapp.di

import com.example.bustrackingapp.common.Constants
import com.example.bustrackingapp.data.remote.api.BusApiService
import com.example.bustrackingapp.data.repository.BusRepositoryImpl
import com.example.bustrackingapp.domain.repository.BusRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideInterceptor() : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun provideOkHttp(interceptor: HttpLoggingInterceptor) : OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit =
         Retrofit.Builder()
             .baseUrl(Constants.apiBaseUrl)
             .client(okHttpClient)
             .addConverterFactory(GsonConverterFactory.create())
             .build()

    @Singleton
    @Provides
    fun provideBusApiService(retrofit: Retrofit) : BusApiService = retrofit.create(BusApiService::class.java)

    @Singleton
    @Provides
    fun provideBusRepository(
        busApiService: BusApiService,
        dispatcher: CoroutineDispatcher
    ) : BusRepository = BusRepositoryImpl(busApiService, dispatcher)

    @Provides
    fun provideIDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}