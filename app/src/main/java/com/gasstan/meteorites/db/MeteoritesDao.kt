package com.gasstan.meteorites.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gasstan.meteorites.model.Meteorite

@Dao
interface MeteoritesDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertMeteorites(meteorites: List<Meteorite>)

  @Query("SELECT * FROM Meteorites")
  suspend fun getMeteorites(): List<Meteorite>

}