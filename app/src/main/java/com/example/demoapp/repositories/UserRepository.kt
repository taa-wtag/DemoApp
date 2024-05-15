package com.example.demoapp.repositories

import com.example.demoapp.UserList
import com.example.demoapp.UserList.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun deleteUser(user: User)

    suspend fun deleteAllUsers()

    fun observeAllUsers(): Flow<UserList>

    suspend fun fetchAllUsersFromRemote()
}