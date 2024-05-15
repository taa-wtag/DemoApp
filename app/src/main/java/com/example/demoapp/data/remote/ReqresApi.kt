package com.example.demoapp.data.remote

import com.example.demoapp.data.remote.response.UserResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ReqresApi
{
    @GET("users")
    suspend fun getUsersByPage(
        @Query("page") searchQuery: String?,
    ): Response<UserResult>
}