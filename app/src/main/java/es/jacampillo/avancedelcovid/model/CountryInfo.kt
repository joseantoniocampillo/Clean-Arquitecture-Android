package es.jacampillo.avancedelcovid.model

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CountryInfo(
    @SerializedName("flag")
    var flag: String?,
    @SerializedName("_id")
    var id: Int?,
    @SerializedName("iso2")
    var iso2: String?,
    @SerializedName("iso3")
    var iso3: String?,
    @SerializedName("lat")
    var lat: Double?,
    @SerializedName("long")
    var long: Double?
)