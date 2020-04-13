package es.jacampillo.avancedelcovid.db_realm


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountryInfo_rm(
    @PrimaryKey
    @SerializedName("_id")
    var id: Int?,
    @SerializedName("iso2")
    var iso2: String?,
    @SerializedName("iso3")
    var iso3: String?,
    @SerializedName("lat")
    var lat: Int?,
    @SerializedName("long")
    var long: Int?,
    @SerializedName("flag")
    var flag: String?
): RealmObject(), Parcelable