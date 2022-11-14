package eu.codeplumbers.kittens_kt.feature_kittens.data.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


/**
 * Network status listener
 * Credits:
 * https://medium.com/@alexzaitsev/android-viewmodel-check-network-connectivity-state-7c028a017cd4
 */
class NetworkListener(private val context: Context) : LiveData<Boolean>() {
    private val mConnected = MutableLiveData<Boolean>()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        // network is available for use
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            mConnected.postValue(true)
        }

        // Network capabilities have changed for the network
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            val unmetered = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
        }

        // lost network connection
        override fun onLost(network: Network) {
            super.onLost(network)
            mConnected.postValue(false)

        }
    }

    init {
        val connectivityManager =
            context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(networkCallback)

        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        mConnected.postValue(activeNetworkInfo != null && activeNetworkInfo.isConnected)
    }

    fun getConnected(): MutableLiveData<Boolean> {
        return mConnected
    }

}