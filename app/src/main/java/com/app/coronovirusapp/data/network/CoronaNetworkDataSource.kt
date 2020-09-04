package com.app.coronovirusapp.data.network

import androidx.lifecycle.LiveData
import com.app.coronovirusapp.data.network.response.CurrentCoronovirusResponse

interface CoronaNetworkDataSource {
    val downloadedCurrentCoronaStats:LiveData<CurrentCoronovirusResponse>

    suspend fun fetchCurrentCoronaStats(
        global:String
    )
}