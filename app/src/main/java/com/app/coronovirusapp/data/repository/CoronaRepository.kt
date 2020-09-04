package com.app.coronovirusapp.data.repository

import androidx.lifecycle.LiveData
import com.app.coronovirusapp.data.db.Result

interface CoronaRepository {

    suspend fun getCurrentCoronaStats():LiveData<Result>
}