package com.gasstan.meteorites.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.DefaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders

private const val TIME_OUT = 60_000

fun provideKtorHttpClient() = HttpClient(Android) {

  install(JsonFeature) {
    serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
      prettyPrint = true
      isLenient = true
      ignoreUnknownKeys = true
    })

    engine {
      connectTimeout = TIME_OUT
      socketTimeout = TIME_OUT
    }
  }

  install(Logging) {
    logger = object : Logger {
      override fun log(message: String) {
        Log.v("Ktor =>", message)
      }
    }
    level = LogLevel.INFO
  }

  install(ResponseObserver) {
    onResponse { response ->
      Log.d("HTTP status:", "${response.status.value}")
    }
  }

  install(DefaultRequest) {
    header(HttpHeaders.ContentType, ContentType.Application.Json)
    header("X-App-Token" , "GiBL88LCC34w38Rjt6A1I0EwD")
    parameter("\$limit", "100000") // download all data
  }
}