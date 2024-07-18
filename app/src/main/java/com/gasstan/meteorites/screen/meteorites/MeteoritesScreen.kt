package com.gasstan.meteorites.screen.meteorites

import android.widget.Space
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.gasstan.meteorites.R
import com.gasstan.meteorites.composables.LoadingDataProgressIndicator
import com.gasstan.meteorites.model.Meteorite
import com.gasstan.meteorites.screen.MeteoritesViewModel
import com.gasstan.meteorites.ui.theme.MeteoritesTheme
import com.gasstan.meteorites.utils.onLoading
import com.gasstan.meteorites.utils.onSuccess
import org.koin.androidx.compose.koinViewModel

@Composable
fun MeteoritesScreen(
  viewModel: MeteoritesViewModel = koinViewModel()
) {
  val meteorites by viewModel.allMeteorites.collectAsStateWithLifecycle()

  meteorites.onSuccess { items ->
    Content(
      meteorites = items,
      onItemClick = { }
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
  onItemClick: (Meteorite) -> Unit,
  modifier: Modifier = Modifier
) {

  LazyColumn(
    modifier = modifier.animateContentSize(),
    contentPadding = PaddingValues(horizontal = MeteoritesTheme.dimens.paddingSmall)
  ) {
    items(meteorites, key = { it.id }) { task ->
      MeteoriteItemComposable(
        item = task,
        onItemClick = onItemClick,
      )
      Spacer(modifier = Modifier.height(MeteoritesTheme.dimens.paddingSmall))
    }
  }
}

@Composable
private fun MeteoriteItemComposable(
  item: Meteorite,
  onItemClick: (Meteorite) -> Unit,
) {
  Card(
    onClick = { onItemClick(item) },
    modifier = Modifier
      .fillMaxWidth()
  ) {
    Column(
      modifier = Modifier.padding(
        horizontal = MeteoritesTheme.dimens.padding,
        vertical = MeteoritesTheme.dimens.paddingSmall
      )
    ) {
      Row(
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(Modifier.weight(0.5f)) {
          Text(
            text = item.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge
          )
          Spacer(modifier = Modifier.height(MeteoritesTheme.dimens.padding))
          Text(text = "Class: ${item.recclass}")
        }

        Column(horizontalAlignment = Alignment.End) {
          TextWithIcon(imageVector = Icons.Outlined.DateRange, text = item.year.toString())
          Spacer(modifier = Modifier.height(MeteoritesTheme.dimens.padding))
          TextWithIcon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_scale),
            text = "${item.mass ?: "?"} g"
          )
        }
      }


    }
  }
}

@Composable
private fun TextWithIcon(
  imageVector: ImageVector,
  text: String,
) {
  Row {
    Text(text = text)
    Spacer(modifier = Modifier.width(MeteoritesTheme.dimens.paddingSmall))
    Icon(imageVector = imageVector, contentDescription = null)
  }
}

@Preview
@Composable
private fun MeteoriteItemComposablePreview() = MeteoritesTheme {
  MeteoriteItemComposable(
    item = Meteorite(
      name = "Lakiesha dsd Pasda",
      id = "Mykel",
      nametype = "Echo",
      recclass = "Angelia",
      mass = 6000f,
      fall = "Fall",
      year = 1800,
      geolocation = null
    )
  ) {
  }
}
