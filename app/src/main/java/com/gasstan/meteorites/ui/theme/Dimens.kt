package com.gasstan.meteorites.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
  val padding: Dp = 16.dp,
  val paddingSmall: Dp = 8.dp,
  val paddingMedium: Dp = 24.dp,
  val paddingLarge: Dp = 32.dp,
)

private val dimens = Dimens()


internal val LocalDimens = staticCompositionLocalOf { dimens }

@Composable
internal fun ProvideDimens(
  content: @Composable () -> Unit,
) {
  CompositionLocalProvider(LocalDimens provides dimens, content = content)
}