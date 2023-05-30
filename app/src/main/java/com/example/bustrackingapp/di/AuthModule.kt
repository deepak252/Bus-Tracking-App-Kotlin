package com.example.bustrackingapp.di

import com.example.bustrackingapp.data.remote.api.AuthApiService
import com.example.bustrackingapp.data.repository.AuthRepositoryImpl
import com.example.bustrackingapp.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Singleton
    @Provides
    fun provideAuthApiService(
        retrofit: Retrofit
    ) : AuthApiService = retrofit.create(AuthApiService::class.java)

    @Singleton
    @Provides
    fun provideAuthRepository(
        authApiService: AuthApiService,
        dispatcher : CoroutineDispatcher
    ) : AuthRepository = AuthRepositoryImpl( authApiService, dispatcher)

}