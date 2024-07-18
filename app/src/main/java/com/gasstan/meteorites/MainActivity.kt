package com.gasstan.meteorites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.gasstan.meteorites.navigation.AppNavHost
import com.gasstan.meteorites.navigation.Destinations
import com.gasstan.meteorites.ui.theme.MeteoritesTheme

class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MeteoritesTheme {
        val navController = rememberNavController()
        var title by remember {
          mutableIntStateOf(Screens.Meteorites.title)
        }
        Scaffold(
          topBar = { TopAppBar(title = { Text(text = stringResource(id = title)) }) },
          bottomBar = {
            BottomNavigation(
              backgroundColor = Color.White,
              elevation = 8.dp
            ) {
              Screens.entries.forEach { screen ->
                BottomNavigationItem(
                  icon = {
                    Icon(
                      painter = painterResource(id = screen.icon),
                      contentDescription = null
                    )
                  },
                  label = { Text(text = stringResource(id = screen.title)) },
                  selected = false,
                  onClick = {
                    title = screen.title
                    navController.navigate(screen.destination.route) {
                      popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                      }
                      launchSingleTop = true
                      restoreState = true
                    }
                  })
              }

            }
          },
          modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
          AppNavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController
          )

        }
      }
    }
  }
}

enum class Screens(
  val destination: Destinations,
  @StringRes val title: Int,
  @DrawableRes val icon: Int
) {
  Meteorites(Destinations.Meteorites, R.string.meteorites, R.drawable.ic_list),
  Stats(Destinations.Stats, R.string.stats, R.drawable.ic_stats),
  Map(Destinations.Map, R.string.map, R.drawable.ic_map)
}