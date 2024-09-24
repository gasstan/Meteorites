package com.gasstan.meteorites.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gasstan.meteorites.model.Meteorite

@Database(entities = [Meteorite::class], version = 1)
@TypeConverters(Converters::class)
abstract class MeteoritesDatabase : RoomDatabase() {
  abstract fun meteoritesDao(): MeteoritesDao
}

fun provideDatabase(context: Context) = Room.databaseBuilder(
  context = context,
  klass = MeteoritesDatabase::class.java,
  name = "MeteoritesDatabase"
).build()