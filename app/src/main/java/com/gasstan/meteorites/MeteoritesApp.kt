package com.gasstan.meteorites

import android.app.Application
import com.gasstan.meteorites.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MeteoritesApp : Application() {
  override fun onCreate() {
    super.onCreate()
    startKoin {
      androidContext(this@MeteoritesApp)
      modules(appModule)
    }
  }
}