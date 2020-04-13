package es.jacampillo.avancedelcovid.models_api_response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
data class CountryInfo(
    @SerializedName("flag")
    var flag: String? = null,
    @SerializedName("_id")
    var id: Int? = null,
    @SerializedName("iso2")
    var iso2: String? = null,
    @SerializedName("iso3")
    var iso3: String? = null,
    @SerializedName("lat")
    var lat: Double? = null,
    @SerializedName("long")
    var long: Double? = null
) : Parcelable