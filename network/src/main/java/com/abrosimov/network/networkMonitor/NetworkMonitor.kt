package com.abrosimov.network.networkMonitor

interface NetworkMonitor {
    suspend fun isOnline(): Boolean
}