package com.abrosimov.impl.networkMonitor

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    suspend fun isOnline(): Boolean
    fun getNetworkStatus(): Flow<Boolean>
}