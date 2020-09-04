package com.app.coronovirusapp.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.app.coronovirusapp.internal.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptorImpl(
    context: Context
) : ConnectivityInterceptor {

    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline())
            throw NoConnectivityException()
        return chain.proceed(chain.request())
    }

    private fun isOnline():Boolean{
        val conntectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
         as ConnectivityManager
        val networkInfo = conntectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}