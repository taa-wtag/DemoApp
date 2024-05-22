package com.example.demoapp.mvvm.repositories

import kotlinx.coroutines.flow.Flow

interface IDataStoreRepository {
    suspend fun addData(t: Any)
    suspend fun deleteData(t: Any)
    suspend fun deleteAllData()
    fun getData(): Flow<Any>
}