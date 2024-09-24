package com.gasstan.meteorites.screen.stats

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gasstan.meteorites.R
import com.gasstan.meteorites.composables.LoadingDataProgressIndicator
import com.gasstan.meteorites.model.Meteorite
import com.gasstan.meteorites.screen.MeteoritesViewModel
import com.gasstan.meteorites.ui.theme.MeteoritesTheme
import com.gasstan.meteorites.utils.onLoading
import com.gasstan.meteorites.utils.onSuccess
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun StatsScreen(viewModel: MeteoritesViewModel = koinViewModel()) {
  val meteorites by viewModel.allMeteorites.collectAsStateWithLifecycle()
  val topTenMeteorites by viewModel.topTenHeaviest.collectAsStateWithLifecycle()

  meteorites.onSuccess {
    Content(
      meteorites = it,
      topTenMeteorites = topTenMeteorites,
    )
  }.onLoading {
    Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      LoadingDataProgressIndicator()
    }
  }
}

@Composable
private fun Content(
  meteorites: List<Meteorite>,
  topTenMeteorites: List<Meteorite>,
) {

  Column(modifier = Modifier.padding(MeteoritesTheme.dimens.paddingSmall)) {
    Text(
      text = stringResource(R.string.landings_since_2011),
      style = MaterialTheme.typography.titleMedium
    )
    Spacer(modifier = Modifier.height(MeteoritesTheme.dimens.paddingSmall))
    MeteoritesSince2011(meteorites = meteorites)
    Spacer(modifier = Modifier.height(MeteoritesTheme.dimens.paddingLarge))
    Text(
      text = stringResource(R.string.top_10_heaviest_meteorites),
      style = MaterialTheme.typography.titleMedium
    )
    Spacer(modifier = Modifier.height(MeteoritesTheme.dimens.paddingSmall))
    TopTenHeaviestMeteorites(topTenMeteorites = topTenMeteorites)
  }
}

@Composable
private fun MeteoritesSince2011(meteorites: List<Meteorite>) {
  val producer = remember { CartesianChartModelProducer() }
  LaunchedEffect(Unit) {
    producer.runTransaction {
      val data =
        meteorites
          .mapNotNull { it.year }
          .groupBy { it }
          .filter { it.key <= LocalDate.now().year }
      lineSeries {
        series(
          x = data.keys,
          y = data.values.map { it.size },
        )
      }
    }
  }
  CartesianChartHost(
    chart =
    rememberCartesianChart(
      rememberLineCartesianLayer(),
      startAxis = rememberStartAxis(guideline = null),
      bottomAxis = rememberBottomAxis(guideline = null, labelRotationDegrees = 90f),
    ),
    modelProducer = producer,
  )
}

@SuppressLint("DefaultLocale")
@Composable
private fun TopTenHeaviestMeteorites(topTenMeteorites: List<Meteorite>) {
  if (topTenMeteorites.isEmpty()) {
    Column(
      modifier = Modifier.fillMaxWidth(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      LoadingDataProgressIndicator()
    }
    return
  }
  Column {
    topTenMeteorites.forEach {
      val mass = it.mass?.div(1000)?.let { m ->
        String.format("%.1f", m)
      } ?: "Unknown"

      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text("${it.name}(${it.year ?: "Unknown"})")
        Text("$mass kg")
      }
    }
  }
}