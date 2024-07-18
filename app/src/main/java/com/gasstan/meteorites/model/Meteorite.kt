package com.gasstan.meteorites.model

import com.gasstan.meteorites.utils.YearSerializer
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meteorite(
  @SerialName("name")
  val name: String,
  @SerialName("id")
  val id: String,
  @SerialName("nametype")
  val nametype: String,
  @SerialName("recclass")
  val recclass: String,
  @SerialName("mass")
  val mass: Float? = null,
  @SerialName("fall")
  val fall: String,
  @SerialName("year")
  @Serializable(with = YearSerializer::class)
  val year: Int? = null,
  @SerialName("geolocation")
  val geolocation: Geolocation? = null
) : ClusterItem {
  override fun getPosition(): LatLng =
    LatLng(geolocation?.latitude?.toDouble() ?: 0.0, geolocation?.longitude?.toDouble() ?: 0.0)

  override fun getTitle(): String = name

  override fun getSnippet(): String = "Year: $year Reclass: $recclass Mass: ${mass ?: "unknown"} g"

  override fun getZIndex(): Float = 10f

}

@Serializable
data class Geolocation(
  @SerialName("latitude")
  val latitude: String,
  @SerialName("longitude")
  val longitude: String
)

