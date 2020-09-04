package com.app.coronovirusapp.ui.CurrentStatsWorld

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.coronovirusapp.data.repository.CoronaRepository
import com.app.coronovirusapp.internal.lazyDeferred

class CurrentStatsViewModel(
    private val coronaRepository: CoronaRepository
) : ViewModel() {

    val corona by lazyDeferred {
        coronaRepository.getCurrentCoronaStats()
    }
}