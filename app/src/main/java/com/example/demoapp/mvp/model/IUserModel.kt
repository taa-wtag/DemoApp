package com.example.demoapp.mvp.model

import com.example.demoapp.UserList
import kotlinx.coroutines.flow.Flow

interface IUserModel {
    suspend fun deleteUserFromDatastore(user: UserList.User)

    suspend fun deleteAllUsersFromDatastore()

    fun observeAllUsers(): Flow<UserList>

    suspend fun fetchAllUsersFromRemote()
}