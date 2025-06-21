package com.abrosimov.financeapp.data.network

interface NetworkMonitor {
    suspend fun isOnline(): Boolean
}