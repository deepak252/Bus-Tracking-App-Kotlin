package com.example.bustrackingapp.common

import android.util.Log
import com.example.bustrackingapp.domain.model.ApiResponse
import com.google.gson.Gson
import retrofit2.HttpException


abstract class NetworkRequest {
    suspend fun <T> getResult(apiCall: suspend () -> ApiResponse<T> ): NetworkResult<T> {
        try {
            val apiResponse = apiCall()
            Log.d("Logger", "apiResponse : ${apiResponse}")
            return NetworkResult.Success( apiResponse.message, apiResponse.data)

        } catch (e: HttpException ) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse =Gson().fromJson(errorBody, ApiResponse::class.java)
            Log.e("Logger","HttpException ${errorResponse}")
            if(errorResponse.message!=null){
                return NetworkResult.Error(errorResponse.message)
            }
            return NetworkResult.Error(e.message ?: e.toString())
        }catch (e : Exception){
            Log.e("Logger","Exception ${e}")
            return NetworkResult.Error(e.message ?: e.toString())
        }

    }
//    private fun <T> error(errorMessage: String): NetworkResult<T> =
//        NetworkResult.Error("Api call failed $errorMessage")
}
