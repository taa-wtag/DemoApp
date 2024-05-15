package com.example.demoapp.repositories

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun addData(t: Any)
    suspend fun deleteData(t: Any)
    suspend fun deleteAllData()
    fun getData(): Flow<Any>
}