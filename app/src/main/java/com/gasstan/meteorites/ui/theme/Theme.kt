package com.gasstan.meteorites.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightColorScheme = lightColorScheme(
  primary = primaryLight,
  onPrimary = onPrimaryLight,
  primaryContainer = primaryContainerLight,
  onPrimaryContainer = onPrimaryContainerLight,
  secondary = secondaryLight,
  onSecondary = onSecondaryLight,
  secondaryContainer = secondaryContainerLight,
  onSecondaryContainer = onSecondaryContainerLight,
  background = backgroundLight,
  onBackground = onBackgroundLight,
  surface = surfaceLight,
  onSurface = onSurfaceLight,
)

@Composable
fun MeteoritesTheme(
  content: @Composable () -> Unit
) {
  ProvideDimens {
    MaterialTheme(
      colorScheme = LightColorScheme,
      typography = Typography,
      content = content
    )
  }
}

object MeteoritesTheme {
  val dimens: Dimens
    @Composable
    get() = LocalDimens.current

}