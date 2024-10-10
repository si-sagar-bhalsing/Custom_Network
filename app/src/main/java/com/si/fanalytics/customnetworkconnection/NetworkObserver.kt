package com.si.fanalytics.customnetworkconnection// NetworkObserver.kt
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NetworkObserver(context: Context) {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val _connectivityStatus = MutableStateFlow(ConnectivityStatus.DISCONNECTED)
    val connectivityStatus: StateFlow<ConnectivityStatus> = _connectivityStatus

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _connectivityStatus.value = ConnectivityStatus.CONNECTED
        }

        override fun onLost(network: Network) {
            _connectivityStatus.value = ConnectivityStatus.DISCONNECTED
        }
    }

    // Method to start listening for network changes
    fun startListening() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    // Method to stop listening when no longer needed
    fun stopListening() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}
