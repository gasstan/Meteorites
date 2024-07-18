package com.gasstan.meteorites.network

import com.gasstan.meteorites.model.Meteorite
import io.ktor.client.HttpClient
import io.ktor.client.request.get


private const val ENDPOINT = "https://data.nasa.gov/resource/gh4g-9sfh.json"

class MeteoritesApi(private val client: HttpClient) {

  suspend fun getMeteorites(): List<Meteorite> = client.get(ENDPOINT)
}