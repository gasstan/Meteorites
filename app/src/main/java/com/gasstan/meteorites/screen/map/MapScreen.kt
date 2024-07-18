package com.gasstan.meteorites.screen.map

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gasstan.meteorites.model.Meteorite
import com.gasstan.meteorites.screen.MeteoritesViewModel
import com.gasstan.meteorites.utils.Resource
import com.gasstan.meteorites.utils.onSuccess
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberCameraPositionState
import org.koin.androidx.compose.koinViewModel

@Composable
fun MapScreen(viewModel: MeteoritesViewModel = koinViewModel()) {
  val meteorites by viewModel.allMeteorites.collectAsStateWithLifecycle()

  Content(meteorites = meteorites)
}

@OptIn(MapsComposeExperimentalApi::class)
@Composable
private fun Content(meteorites: Resource<List<Meteorite>>) {
  val cameraPositionState = rememberCameraPositionState {
    position = CameraPosition.fromLatLngZoom(LatLng(50.0755, 14.4378), 6f)
  }

  GoogleMap(
    cameraPositionState = cameraPositionState,
  ) {
    meteorites.onSuccess {
      Clustering(
        items = it
      )
    }
  }
}