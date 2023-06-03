package com.example.bustrackingapp.feature_auth.di

import com.example.bustrackingapp.feature_auth.data.remote.api.AuthApiService
import com.example.bustrackingapp.feature_auth.data.repository.AuthRepositoryImpl
import com.example.bustrackingapp.feature_auth.domain.repository.AuthRepository
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