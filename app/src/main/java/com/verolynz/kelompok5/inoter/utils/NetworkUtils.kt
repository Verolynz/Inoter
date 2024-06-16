package com.verolynz.kelompok5.inoter.utils
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.net.HttpURLConnection
import java.net.URL

object NetworkUtils {
    fun isConnectedToInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
    fun hasInternetAccess(): Boolean {
        try {
            val url = URL("http://www.google.com")
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.setConnectTimeout(1000)
            urlConnection.connect()
            return urlConnection.responseCode == 200
        } catch (e: Exception) {
            return false
        }
    }
}