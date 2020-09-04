package com.app.coronovirusapp.ui.CurrentStatsWorld

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.coronovirusapp.data.repository.CoronaRepository

class CurrentCoronaStatsViewModelFactory(
    private val coronaRepository: CoronaRepository
):ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentStatsViewModel(coronaRepository) as T
    }
}