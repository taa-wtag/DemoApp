package com.example.demoapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://reqres.in/api/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object ReqresApiClient {
    val reqresApiService: ReqresApi by lazy {
        RetrofitClient.retrofit.create(ReqresApi::class.java)
    }
}