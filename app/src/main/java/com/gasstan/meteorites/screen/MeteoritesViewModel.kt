package com.gasstan.meteorites.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gasstan.meteorites.model.Meteorite
import com.gasstan.meteorites.repository.MeteoritesRepository
import com.gasstan.meteorites.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MeteoritesViewModel(meteoritesRepository: MeteoritesRepository) : ViewModel() {

  val allMeteorites: StateFlow<Resource<List<Meteorite>>> =
    meteoritesRepository.getMeteorites()
      .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Resource.Empty())


  val topTenHeaviest: StateFlow<List<Meteorite>> = allMeteorites.map {
    val data = it.getDataOrNull() ?: return@map emptyList<Meteorite>()
    data.parallelStream()
      .sorted { o1, o2 -> (o2.mass ?: 0f).compareTo(o1.mass ?: 0f) }
      .toList()
      .take(10)
  }.flowOn(Dispatchers.IO)
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
}