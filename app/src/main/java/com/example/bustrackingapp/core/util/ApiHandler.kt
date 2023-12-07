package com.example.bustrackingapp.core.util

import com.example.bustrackingapp.core.data.remote.dto.ApiResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

abstract class ApiHandler {
    private val logger = LoggerUtil(c = "ApiHandler")

    fun <T> makeRequest(
        apiCall: suspend () -> ApiResponse<T>,
        onSuccess : (suspend (T)->Unit)?=null,
    ): Flow<Resource<T>> = flow{
        try {
            emit(Resource.Loading())
            val apiResponse = apiCall()
            //success
            logger.info("res : $apiResponse")
            if (onSuccess != null) {
                onSuccess(apiResponse.data)
            }
            emit( Resource.Success( apiResponse.data, apiResponse.message ))

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)
            logger.error("HttpException $errorResponse")
            if(errorResponse.message!=null){
                emit( Resource.Error( errorResponse.message))
            }else{
                emit( Resource.Error(e.message ?: e.toString() ))
            }
        }catch (e : Exception){
            logger.error("Exception $e")
            emit( Resource.Error( e.message ?: e.toString() ))
        }

    }
}