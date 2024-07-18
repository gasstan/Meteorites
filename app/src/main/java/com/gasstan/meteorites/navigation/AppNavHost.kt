package com.gasstan.meteorites.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gasstan.meteorites.screen.map.MapScreen
import com.gasstan.meteorites.screen.meteorites.MeteoritesScreen
import com.gasstan.meteorites.screen.stats.StatsScreen

@Composable
fun AppNavHost(
  modifier: Modifier = Modifier,
  navController: NavHostController,
  startDestination: String = Destinations.Meteorites.route,
) {
  NavHost(
    modifier = modifier,
    navController = navController,
    startDestination = startDestination
  ) {
    composable(Destinations.Meteorites.route) {
      MeteoritesScreen()
    }

    composable(Destinations.Stats.route) {
      StatsScreen()
    }

    composable(Destinations.Map.route) {
      MapScreen()
    }

  }
}

sealed class Destinations(val route: String) {
  object Meteorites : Destinations("meteorites")
  object Stats : Destinations("stats")
  object Map : Destinations("map")
}