package com.gasstan.meteorites.di

import android.content.Context
import com.gasstan.meteorites.dataStore.dataStore
import com.gasstan.meteorites.db.provideDatabase
import com.gasstan.meteorites.network.MeteoritesApi
import com.gasstan.meteorites.network.provideKtorHttpClient
import com.gasstan.meteorites.repository.MeteoritesRepository
import com.gasstan.meteorites.screen.MeteoritesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
  single { provideKtorHttpClient() }
  single { provideDatabase(get()) }

  single { MeteoritesApi(get()) }
  single { MeteoritesRepository(get(), get(), get<Context>().dataStore) }

  viewModel { MeteoritesViewModel(get()) }
}