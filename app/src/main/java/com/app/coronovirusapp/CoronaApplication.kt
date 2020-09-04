package com.app.coronovirusapp

import android.app.Application
import com.app.coronovirusapp.data.db.CoronaDatabase
import com.app.coronovirusapp.data.network.*
import com.app.coronovirusapp.data.repository.CoronaRepository
import com.app.coronovirusapp.data.repository.CoronaRepositoryImpl
import com.app.coronovirusapp.ui.CurrentStatsWorld.CurrentCoronaStatsViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class CoronaApplication:Application(),KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@CoronaApplication))

        bind() from singleton { CoronaDatabase(instance()) }
        bind() from singleton { instance<CoronaDatabase>().currentCoronaDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { CoronovirusApiService(instance()) }
        bind<CoronaNetworkDataSource>() with singleton { CoronaNetworkDataSourceImpl(instance()) }
        bind<CoronaRepository>() with singleton { CoronaRepositoryImpl(instance(),instance()) }
        bind() from provider { CurrentCoronaStatsViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}