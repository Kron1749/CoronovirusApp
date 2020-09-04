package com.app.coronovirusapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrentCoronaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(coronaEntry: List<Result>)

    @Query("select * from current_corona where id = $CURRENT_CORONA_ID")
    fun getCoronaStat():LiveData<Result>
}