package com.example.bustrackingapp.di

import com.example.bustrackingapp.data.remote.api.UserApiService
import com.example.bustrackingapp.data.repository.UserRepositoryImpl
import com.example.bustrackingapp.domain.repository.UserRepository
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

}