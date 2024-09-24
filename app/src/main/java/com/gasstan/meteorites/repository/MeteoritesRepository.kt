package com.gasstan.meteorites.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.gasstan.meteorites.dataStore.getDataTimeStamp
import com.gasstan.meteorites.dataStore.saveTimeStamp
import com.gasstan.meteorites.db.MeteoritesDatabase
import com.gasstan.meteorites.model.Meteorite
import com.gasstan.meteorites.network.MeteoritesApi
import com.gasstan.meteorites.utils.Resource
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.ServerResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDate


class MeteoritesRepository(
  private val meteoritesApi: MeteoritesApi,
  private val meteoritesDb: MeteoritesDatabase,
  private val dataStore: DataStore<Preferences>
) {

  fun getMeteorites(): Flow<Resource<List<Meteorite>>> =
    flow {
      emit(Resource.Loading())
      val timestamp = dataStore.getDataTimeStamp()
      val meteorites: List<Meteorite> =
        try {
          if (timestamp == null || timestamp.isBefore(LocalDate.now())) {
            meteoritesApi.getMeteorites()
              .filter {
                if (it.year == null) false
                else it.year >= 2011
              }
              .also {
                meteoritesDb.meteoritesDao().insertMeteorites(it)
                dataStore.saveTimeStamp(LocalDate.now())
              }
          } else {
            meteoritesDb.meteoritesDao().getMeteorites()
          }
        } catch (e: ServerResponseException) {
          emit(Resource.Error(e.localizedMessage ?: "Unknown exception"))
          return@flow
        } catch (e: ClientRequestException) {
          emit(Resource.Error("${e.response.status} ${e.localizedMessage ?: "Unknown exception"}"))
          return@flow
        }

      if (meteorites.isEmpty()) {
        emit(Resource.Empty())
      } else {
        emit(Resource.Success(meteorites))
      }
    }.flowOn(Dispatchers.IO)
}

