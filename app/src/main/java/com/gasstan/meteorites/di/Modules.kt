package com.gasstan.meteorites.di

import com.gasstan.meteorites.network.MeteoritesApi
import com.gasstan.meteorites.network.provideKtorHttpClient
import com.gasstan.meteorites.repository.MeteoritesRepository
import com.gasstan.meteorites.screen.MeteoritesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
  single { provideKtorHttpClient() }

  single { MeteoritesApi(get()) }
  single { MeteoritesRepository(get()) }

  viewModel { MeteoritesViewModel(get()) }
}