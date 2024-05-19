package com.example.demoapp.data.remote

import com.example.demoapp.other.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object UserApiClient {
    val userApiService: UserApiService by lazy {
        RetrofitClient.retrofit.create(UserApiService::class.java)
    }
}