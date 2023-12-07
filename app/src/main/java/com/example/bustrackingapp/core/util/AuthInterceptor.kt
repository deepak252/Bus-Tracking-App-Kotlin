package com.example.bustrackingapp.core.util

import com.example.bustrackingapp.core.domain.repository.UserPrefsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.Exception
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val userPrefsRepository: UserPrefsRepository
) : Interceptor {
    private val logger = LoggerUtil(c ="AuthInterceptor")

    override fun intercept(chain: Interceptor.Chain): Response {
        try{
            var request = chain.request()
            val url = request.url().toString()

            runBlocking {
//                logger.info("Thread : ${Thread.currentThread().name}")
//                delay(1000)
                if(!url.contains("/auth/")){
                    val token = userPrefsRepository.getToken.first()
                    request = request
                        .newBuilder()
                        .addHeader("Authorization", token)
                        .build()
                }
            }

//            logger.info("request : ${request.url()}")
            return chain.proceed(request)
        }catch (e : Exception){
            throw e
        }
    }

//    override fun intercept(chain: Interceptor.Chain): Response {
//        try {
//            val request = chain.request()
//            val response = chain.proceed(request)
//            logger.info("${response.body()}")
//            return response
//        } catch (e: Exception) {
//            logger.error("Exception occurred: ${e.message}")
//            throw e
//        }
//    }
}
