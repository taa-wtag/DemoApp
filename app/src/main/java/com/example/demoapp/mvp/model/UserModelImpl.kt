package com.example.demoapp.mvp.model

import android.content.Context
import android.util.Log
import androidx.datastore.core.IOException
import com.example.demoapp.UserList
import com.example.demoapp.data.local.DataStoreSingleton.dataStore
import com.example.demoapp.data.remote.response.UserInfo
import com.example.demoapp.other.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class UserModelImpl(
    private val context: Context,
    private val remoteServerEvent: RemoteServerEvent
): IUserModel {
    private val userListFlow: Flow<UserList> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e("Error", exception.toString())
                emit(UserList.getDefaultInstance())
            } else {
                throw exception
            }
        }
    override suspend fun deleteUserFromDatastore(user: UserList.User) {
            val userIndex = userListFlow.first().usersList.indexOf(user)
            if(userIndex!=-1) {
                context.dataStore.updateData { preferences ->
                    preferences.toBuilder().removeUsers(userIndex).build()
                }
            }
            else throw Exception("No such User exists!")
    }

    override suspend fun deleteAllUsersFromDatastore() {
        context.dataStore.updateData { preferences ->
            preferences.toBuilder().clearUsers().build()
        }
    }

    override fun observeAllUsers(): Flow<UserList> {
        return userListFlow
    }

    override suspend fun fetchAllUsersFromRemote() {
        val result = remoteServerEvent.fetchAllUsersFromRemote()
        if (result.status== Status.SUCCESS){
            result.data?.userData?.forEach {
                addData(it)
            }
        }
    }

    private suspend fun addData(t: Any ) {
        when (t) {
            is UserList.User -> {
                if(!checkIfUserExists(t.id)) {
                    context.dataStore.updateData { preferences ->
                        preferences.toBuilder().addUsers(t).build()
                    }
                }
                else throw Exception("User exists!")
            }
            is UserList -> {
                context.dataStore.updateData { preferences ->
                    preferences.toBuilder().addAllUsers(t.usersList.filter { !checkIfUserExists(it.id) }).build()
                }
            }

            is UserInfo -> {
                if(!t.id?.let { checkIfUserExists(it) }!!) {
                    context.dataStore.updateData { preferences ->
                        val newUser = UserList.User.newBuilder()
                        newUser.setAvatar(t.avatar)
                        newUser.setEmail(t.email)
                        newUser.setId(t.id)
                        newUser.setFirstName(t.firstName)
                        newUser.setLastName(t.lastName)
                        preferences.toBuilder().addUsers(newUser).build()
                    }
                }
            }

            else -> throw Exception("Invalid type argument!")
        }
    }
    private suspend fun checkIfUserExists(id: Int): Boolean{
        val user = userListFlow.firstOrNull()?.usersList?.find {
            it.id == id
        }
        return user!=null
    }
}