package com.example.demoapp.mvvm.repositories

import com.example.demoapp.UserList
import com.example.demoapp.UserList.User
import com.example.demoapp.data.remote.ReqresApi
import com.example.demoapp.other.Resource
import com.example.demoapp.other.Status
import com.example.demoapp.repositories.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class DefaultUserRepository(
    private val userDataStoreRepository: DataStoreRepository,
    private val reqresApi: ReqresApi
): UserRepository {

    override suspend fun deleteUser(user: User) {
        userDataStoreRepository.deleteData(user)
    }

    override suspend fun deleteAllUsers() {
        userDataStoreRepository.deleteAllData()
    }

    override fun observeAllUsers(): Flow<UserList> {
        return userDataStoreRepository.getData() as Flow<UserList>
    }

    override suspend fun fetchAllUsersFromRemote() {
        val result = try {
            val response = reqresApi.getUsersByPage("1")
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

        if (result.status==Status.SUCCESS){
            result.data?.userData?.forEach {
                userDataStoreRepository.addData(it)
            }
        }
    }
}