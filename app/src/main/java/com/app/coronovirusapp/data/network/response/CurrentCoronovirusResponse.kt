package com.app.coronovirusapp.data.network.response

import com.app.coronovirusapp.data.db.Result


data class CurrentCoronovirusResponse(
    val results: List<Result>,
    val stat: String
)