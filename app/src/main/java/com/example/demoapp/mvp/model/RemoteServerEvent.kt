package com.example.demoapp.mvp.model

import com.example.demoapp.data.remote.UserApiService
import com.example.demoapp.data.remote.response.UserListResponse
import com.example.demoapp.other.Resource

class RemoteServerEvent(private val userApi: UserApiService) {
    suspend fun fetchAllUsersFromRemote(): Resource<UserListResponse>{
        return try {
            val response = userApi.getUsersByPage("1")
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occurred", null)
            } else {
                Resource.error("An unknown error occurred", null)
            }
        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }
}