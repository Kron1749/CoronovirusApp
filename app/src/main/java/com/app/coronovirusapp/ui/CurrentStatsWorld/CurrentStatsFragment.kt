package com.app.coronovirusapp.ui.CurrentStatsWorld

import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.coronovirusapp.R
import com.app.coronovirusapp.data.network.ConnectivityInterceptorImpl
import com.app.coronovirusapp.data.network.CoronaNetworkDataSource
import com.app.coronovirusapp.data.network.CoronaNetworkDataSourceImpl
import com.app.coronovirusapp.data.network.CoronovirusApiService
import com.app.coronovirusapp.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentStatsFragment : ScopedFragment(), KodeinAware {


    override val kodein by closestKodein()

    private val viewModelFactory: CurrentCoronaStatsViewModelFactory by instance()


    private lateinit var currentStatsViewModel: CurrentStatsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        currentStatsViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CurrentStatsViewModel::class.java)

        bindUI()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun bindUI() = launch {
        val currentCoronaStats = currentStatsViewModel.corona.await()
        currentCoronaStats.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            group_loading.visibility = View.GONE
            (activity as? AppCompatActivity)?.supportActionBar?.title = "Global"
            (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "All world"



            textView_total_countries_affected.text =
                "total countries affected ${it.totalAffectedCountries.toString()}"
            textView_total_cases.text = "total cases ${it.totalCases.toString()}"
            textView_total_deaths.text = "total deaths ${it.totalDeaths.toString()}"
            textView_total_recovered.text = "total recovered ${it.totalRecovered.toString()}"
            textView_unresolved.text = "total unresolved ${it.totalUnresolved.toString()}"
            textView_corona_stats.text = "Coronovirus Stats"
            imageView_condition_icon.setImageIcon(
                Icon.createWithResource(
                    activity,
                    R.drawable.corona
                )
            )

        })
    }
}

