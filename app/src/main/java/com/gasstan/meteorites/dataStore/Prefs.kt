package com.gasstan.meteorites.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import java.time.LocalDate

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

private val DATA_TIMESTAMP = stringPreferencesKey("dataTimeStamp")

suspend fun DataStore<Preferences>.saveTimeStamp(timestamp: LocalDate) =
  edit { settings ->
    settings[DATA_TIMESTAMP] = timestamp.toString()
  }

suspend fun DataStore<Preferences>.getDataTimeStamp(): LocalDate? =
  data.first()[DATA_TIMESTAMP]?.let { LocalDate.parse(it) }

