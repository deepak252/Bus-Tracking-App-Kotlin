package com.example.bustrackingapp.feature_profile.di

import com.example.bustrackingapp.feature_profile.data.remote.api.UserApiService
import com.example.bustrackingapp.feature_profile.data.repository.UserRepositoryImpl
import com.example.bustrackingapp.feature_profile.domain.repository.UserRepository
import com.example.bustrackingapp.feature_profile.domain.use_case.GetUserUseCase
import com.example.bustrackingapp.feature_profile.domain.use_case.ProfileUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    @Singleton
    fun provideUserApiService(
        retrofit: Retrofit
    ) : UserApiService = retrofit.create(UserApiService::class.java)

    @Provides
    @Singleton
    fun provideUserRepository(
        userApiService : UserApiService,
        dispatcher: CoroutineDispatcher
    ) : UserRepository = UserRepositoryImpl(userApiService, dispatcher)

    @Provides
    @Singleton
    fun provideProfileUseCases(
        getUserUseCase: GetUserUseCase
    ) : ProfileUseCases = ProfileUseCases(
        getProfile = getUserUseCase
    )

}