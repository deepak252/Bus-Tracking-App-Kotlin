package com.example.bustrackingapp.core.util

import com.example.bustrackingapp.core.domain.models.Bus
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object GsonUtil {
    val gson = Gson()

    inline fun <reified T> parse(data : String): T{
        val type = object : TypeToken<T>() {}.type
        return gson.fromJson(data, type)
    }

    inline fun < reified T> parseList(data : String): List<T>{
        val type = object : TypeToken<List<T>>() {}.type
        val res  = gson.fromJson<List<T>>(data,type)
        return res.toList()
    }


}