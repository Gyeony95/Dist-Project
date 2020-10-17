package com.avon.remindfeedback.Network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.POST


interface ServiceAPI {
    //test
    @POST("exchange/rate/KRW/USD.json")
    fun getExchange(): Call<Object>

}