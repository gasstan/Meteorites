package com.gasstan.meteorites.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.gasstan.meteorites.R

@Composable
fun LoadingDataProgressIndicator() {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    CircularProgressIndicator()
    Text(text = stringResource(R.string.loading_data))
  }
}