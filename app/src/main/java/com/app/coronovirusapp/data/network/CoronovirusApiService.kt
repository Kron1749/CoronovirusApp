package com.app.coronovirusapp.data.network

import com.app.coronovirusapp.data.network.response.CurrentCoronovirusResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


//https://api.thevirustracker.com/free-api?global=stats
interface CoronovirusApiService {

    @GET("free-api")
    fun getCurrentStats(
        @Query("global") global:String = "stats"
    ) : Deferred<CurrentCoronovirusResponse>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): CoronovirusApiService {
            val requestInterceptor = Interceptor{ chain ->

                val url = chain.request()
                    .url().newBuilder().build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.thevirustracker.com/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CoronovirusApiService::class.java)
        }
    }

}