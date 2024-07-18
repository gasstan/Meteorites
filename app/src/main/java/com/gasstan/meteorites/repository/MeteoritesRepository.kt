package com.gasstan.meteorites.repository

import com.gasstan.meteorites.model.Meteorite
import com.gasstan.meteorites.network.MeteoritesApi
import com.gasstan.meteorites.utils.Resource
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.ServerResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MeteoritesRepository(
  private val meteoritesApi: MeteoritesApi,
) {
  private var cache = listOf<Meteorite>()

  fun getMeteorites(): Flow<Resource<List<Meteorite>>> =
    flow {
      emit(Resource.Loading())
      val meteorites: List<Meteorite> =
        cache.ifEmpty {
          try {
            meteoritesApi.getMeteorites().also {
              cache = it
            }
          } catch (e: ServerResponseException) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown exception"))
            return@flow
          } catch (e: ClientRequestException) {
            emit(Resource.Error("${e.response.status} ${e.localizedMessage ?: "Unknown exception"}"))
            return@flow
          }
        }

      if (meteorites.isEmpty()) {
        emit(Resource.Empty())
      } else {
        emit(Resource.Success(meteorites))
      }
    }.flowOn(Dispatchers.IO)
}

