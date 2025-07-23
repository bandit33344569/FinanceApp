package com.abrosimov.impl.networkMonitor

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Реализация монитора сети, которая проверяет наличие интернет-соединения на устройстве Android.
 *
 * Использует [ConnectivityManager] и [NetworkCapabilities] для проверки доступности интернета.
 * Проверка выполняется асинхронно с использованием корутин в диспетчере [Dispatchers.IO].
 *
 * @property context Контекст приложения, используемый для получения системных сервисов.
 */
class AndroidNetworkMonitor @Inject constructor(
    private val context: Context
) : NetworkMonitor {
    /**
     * Асинхронно проверяет, подключено ли устройство к интернету.
     *
     * Проверка выполняется в IO-диспетчере корутин.
     *
     * @return `true`, если устройство имеет возможность выходить в интернет, иначе `false`
     */
    override suspend fun isOnline(): Boolean {
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
        return withContext(Dispatchers.IO) {
            val network = connectivityManager.activeNetwork ?: return@withContext false
            val capabilities =
                connectivityManager.getNetworkCapabilities(network) ?: return@withContext false
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }
    }
    override fun getNetworkStatus(): Flow<Boolean> = callbackFlow {
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                val capabilities = connectivityManager.getNetworkCapabilities(network)
                val isOnline = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
                trySend(isOnline)
            }

            override fun onLost(network: Network) {
                trySend(false)
            }

            override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
                val isOnline = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                trySend(isOnline)
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, networkCallback)
        trySend(isOnline())
        awaitClose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }
}