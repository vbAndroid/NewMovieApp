package com.example.movielistapp.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import javax.inject.Inject

enum class NetworkState {
    WIFI_CONNECTED,
    MOBILE_DATA_CONNECTED,
    DISCONNECTED
}

/**
 * To Detect Network Changes
 * */
class NetworkStatusHelper @Inject constructor(context: Context) : LiveData<NetworkState>() {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager

    /**
     * Check Network is connected or not
     * */
    val isNetworkConnected: Boolean
        get() = when (getNetworkState()) {
            NetworkState.WIFI_CONNECTED, NetworkState.MOBILE_DATA_CONNECTED -> true
            else -> false
        }

    /**
     * CHECK INTERNET CONNECTION STATUS
     */
    fun getNetworkState(): NetworkState {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            checkNetworkState(network)
        } else {
            checkNetworkState()
        }
    }

    /**
     * Network Callback
     * */
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network) {
            super.onLost(network)
            postValue(NetworkState.DISCONNECTED)
        }

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            postValue(checkNetworkState(network))
        }

        override fun onUnavailable() {
            super.onUnavailable()
            postValue(NetworkState.DISCONNECTED)
        }
    }

    /**
     * Check Network Type
     * */
    private fun checkNetworkState(network: Network? = null): NetworkState {
        if (network != null) {
            val actNw = connectivityManager.getNetworkCapabilities(network)

            if (actNw != null) {
                if (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return NetworkState.WIFI_CONNECTED
                }

                if (actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return NetworkState.MOBILE_DATA_CONNECTED
                }

            }

        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            if (networkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected) {
                return NetworkState.MOBILE_DATA_CONNECTED
            }
        }

        return NetworkState.DISCONNECTED
    }

    /**
     * LiveData: Callbacks - START
     * */
    override fun onActive() {
        super.onActive()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val networkRequest = NetworkRequest.Builder().build()
            connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
        }
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
    /** LiveData: Callbacks - END */


}