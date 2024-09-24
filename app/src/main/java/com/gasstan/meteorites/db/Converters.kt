package com.gasstan.meteorites.db

import androidx.room.TypeConverter
import com.gasstan.meteorites.model.Geolocation

class Converters {
  @TypeConverter
  fun fromGeoLocation(value: String?): Geolocation? {
    return value?.let { Geolocation.parse(it) }
  }

  @TypeConverter
  fun dateToTimestamp(value: Geolocation?): String {
    return value.toString()
  }
}