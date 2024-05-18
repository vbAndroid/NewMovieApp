package com.example.movielistapp.mvvm.base

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movielistapp.Utils.*
import com.example.movielistapp.network.NetworkState
import com.example.movielistapp.network.NetworkStatusHelper


abstract class BaseViewModel( networkStatusHelper: NetworkStatusHelper) : ViewModel() {

    val mNetworkErrorData = MutableLiveData<Boolean>()
    val errorDialogConfig = MutableLiveData<String>()


    var showNoInternetSnackBar = ObservableBoolean(false)

    /** Error View: Retry Click */
    open fun onErrorButtonClick() {/* Ignore */
    }

    init {
        /* Observer NetworkState Change */
        networkStatusHelper.observeForever {
            it?.let { onNetworkState(it) }
        }

        /* Need to Notify First Time
        *  Because NetworkStatusHelper doesn't send callback if network is disconnected
        *  while launching the app */
        networkStatusHelper.getNetworkState().let {
            if (it == NetworkState.DISCONNECTED)
                onNetworkState(it)
        }
    }

    /**
     * Update LiveData when State change
     * */
    private fun onNetworkState(networkState: NetworkState) {
        when (networkState) {
            NetworkState.WIFI_CONNECTED,
            NetworkState.MOBILE_DATA_CONNECTED -> {
                mNetworkErrorData.postValue(true)
            }

            NetworkState.DISCONNECTED -> {
                mNetworkErrorData.postValue(false)
            }
        }
    }
}