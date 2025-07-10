package com.abrosimov.impl.networkMonitor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
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
}