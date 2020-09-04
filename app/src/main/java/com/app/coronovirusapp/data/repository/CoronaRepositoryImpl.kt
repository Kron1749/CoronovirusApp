package com.app.coronovirusapp.data.repository

import androidx.lifecycle.LiveData
import com.app.coronovirusapp.data.db.CurrentCoronaDao
import com.app.coronovirusapp.data.db.Result
import com.app.coronovirusapp.data.network.CoronaNetworkDataSource
import com.app.coronovirusapp.data.network.response.CurrentCoronovirusResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.time.chrono.ChronoZonedDateTime

class CoronaRepositoryImpl(
    private val currentCoronaDao: CurrentCoronaDao,
    private val coronaNetworkDataSource: CoronaNetworkDataSource
) : CoronaRepository {


    init {
        coronaNetworkDataSource.downloadedCurrentCoronaStats.observeForever { newCurrentCoronaStats ->
            persistFetchedCurrentCoronaStats(newCurrentCoronaStats)
        }
    }

    override suspend fun getCurrentCoronaStats(): LiveData<Result> {
        return withContext(Dispatchers.IO) {
            initCoronaData()
            return@withContext currentCoronaDao.getCoronaStat()
        }

    }

    private fun persistFetchedCurrentCoronaStats(fetchedCoronaStats:CurrentCoronovirusResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentCoronaDao.upsert(fetchedCoronaStats.results)
        }
    }

    private suspend fun initCoronaData(){
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentCoronaStats()
    }

    private suspend fun fetchCurrentCoronaStats(){
        coronaNetworkDataSource.fetchCurrentCoronaStats(
            "stats"
        )
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime):Boolean {
        val fiveMinutesAgo = ZonedDateTime.now().minusMinutes(5)
        return lastFetchTime.isBefore(fiveMinutesAgo)
    }
}