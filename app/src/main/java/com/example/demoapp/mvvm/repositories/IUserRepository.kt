package com.example.demoapp.mvvm.repositories

import com.example.demoapp.UserList
import com.example.demoapp.UserList.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    suspend fun deleteUser(user: User)

    suspend fun deleteAllUsers()

    fun observeAllUsers(): Flow<UserList>

    suspend fun fetchAllUsersFromRemote()
}