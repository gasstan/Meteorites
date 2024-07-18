package com.gasstan.meteorites.utils

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = LocalDateTime::class)
object YearSerializer : KSerializer<Int?> {

  override fun deserialize(decoder: Decoder): Int? {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
    val date = dateFormat.parse(
      decoder.decodeString()
    ) ?: return null
    val calendar = Calendar.getInstance().apply { time = date }
    return calendar.get(Calendar.YEAR)
  }
}
