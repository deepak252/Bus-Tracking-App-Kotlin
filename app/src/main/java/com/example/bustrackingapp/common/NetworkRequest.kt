package com.example.bustrackingapp.common

import com.example.bustrackingapp.domain.model.ApiResponse
import com.example.bustrackingapp.utils.CustomLogger
import com.google.gson.Gson
import retrofit2.HttpException


abstract class NetworkRequest {
    private val logger = CustomLogger(c = "NetworkRequest")
    suspend fun <T> getResult(apiCall: suspend () -> ApiResponse<T> ): NetworkResult<T> {
        try {
            val apiResponse = apiCall()
            logger.info("apiResponse : $apiResponse")
            return NetworkResult.Success( apiResponse.message, apiResponse.data)

        } catch (e: HttpException ) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse =Gson().fromJson(errorBody, ApiResponse::class.java)
            logger.error("HttpException $errorResponse")
            if(errorResponse.message!=null){
                return NetworkResult.Error(errorResponse.message)
            }
            return NetworkResult.Error(e.message ?: e.toString())
        }catch (e : Exception){
            logger.error("Exception $e")
            return NetworkResult.Error(e.message ?: e.toString())
        }

    }
//    private fun <T> error(errorMessage: String): NetworkResult<T> =
//        NetworkResult.Error("Api call failed $errorMessage")
}
