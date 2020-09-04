package com.app.coronovirusapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.coronovirusapp.data.network.response.CurrentCoronovirusResponse
import com.app.coronovirusapp.internal.NoConnectivityException

class CoronaNetworkDataSourceImpl(
    private val CoronovirusApiService:CoronovirusApiService
) : CoronaNetworkDataSource {

    private val _downloadedCurrentCoronaStats = MutableLiveData<CurrentCoronovirusResponse>()

    override val downloadedCurrentCoronaStats: LiveData<CurrentCoronovirusResponse>
        get() = _downloadedCurrentCoronaStats

    override suspend fun fetchCurrentCoronaStats(global: String) {
        try {
            val fetchedCurrentCoronaStats = CoronovirusApiService
                .getCurrentStats(global)
                .await()
            _downloadedCurrentCoronaStats.postValue(fetchedCurrentCoronaStats)
        }
        catch (e:NoConnectivityException) {
            Log.e("Connectivty","No internet connection",e)
        }
    }
}