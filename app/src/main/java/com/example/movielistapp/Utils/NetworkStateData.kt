package com.example.movielistapp.Utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import javax.inject.Inject

enum class ConnectivityState {
    WIFI_CONNECTED,
    MOBILE_DATA_CONNECTED,
    DISCONNECTED
}



/**
 * To Detect Network Changes
 * */
class NetworkStateData @Inject constructor(context: Context) : LiveData<ConnectivityState>() {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager

    /**
     * Check Network is connected or not
     * */
    val isNetworkConnected: Boolean
        get() = when (getConnectivityState()) {
            ConnectivityState.WIFI_CONNECTED, ConnectivityState.MOBILE_DATA_CONNECTED -> true
            else -> true
        }

    /**
     * CHECK INTERNET CONNECTION STATUS
     */
    fun getConnectivityState(): ConnectivityState {
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
            postValue(ConnectivityState.DISCONNECTED)
        }

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            postValue(checkNetworkState(network))
        }

        override fun onUnavailable() {
            super.onUnavailable()
            postValue(ConnectivityState.DISCONNECTED)
        }
    }

    /**
     * Check Network Type
     * */
    private fun checkNetworkState(network: Network? = null): ConnectivityState {
        if (network != null) {
            val actNw = connectivityManager.getNetworkCapabilities(network)

            if (actNw != null) {
                if (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return ConnectivityState.WIFI_CONNECTED
                }

                if (actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return ConnectivityState.MOBILE_DATA_CONNECTED
                }
            }
        } else {

            /*
            * Need to use this deprecated method:
            * ConnectivityManager#activeNetwork available in only API 23
            * */
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo

            /*
            * Need to use this deprecated method:
            * ConnectivityManager#activeNetwork available in only API 23
            * */
            @Suppress("DEPRECATION")
            if (networkInfo?.isConnected == true) {
//               "NETWORK CONNECTED"
                return ConnectivityState.MOBILE_DATA_CONNECTED
            }
        }

//       "NETWORK DISCONNECTED"
        return ConnectivityState.DISCONNECTED
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