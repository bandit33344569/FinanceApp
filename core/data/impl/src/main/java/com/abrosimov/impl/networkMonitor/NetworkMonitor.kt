package com.abrosimov.impl.networkMonitor

interface NetworkMonitor {
    suspend fun isOnline(): Boolean
}