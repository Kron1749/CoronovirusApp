package com.app.coronovirusapp.data.db


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


const val CURRENT_CORONA_ID = 0

@Entity(tableName = "current_corona")
data class Result(
    @SerializedName("total_affected_countries")
    val totalAffectedCountries: Int,
    @SerializedName("total_cases")
    val totalCases: Int,
    @SerializedName("total_deaths")
    val totalDeaths: Int,
    @SerializedName("total_recovered")
    val totalRecovered: Int,
    @SerializedName("total_unresolved")
    val totalUnresolved: Int
) {
    @PrimaryKey(autoGenerate = false)
    var id:Int = CURRENT_CORONA_ID
}