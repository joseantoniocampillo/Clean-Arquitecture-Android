package es.jacampillo.avancedelcovid.db_realm

import com.google.gson.annotations.SerializedName
import es.jacampillo.avancedelcovid.models_api_response.CountryInfo
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

data class PaisRmObj(
    @PrimaryKey
    @SerializedName("country")
    var country: String?,
    @SerializedName("updated")
    var updated: Long?,
    @SerializedName("countryInfo")
    var countryInfoRm: CountryInfo?,
    @SerializedName("cases")
    var cases: Int?,
    @SerializedName("todayCases")
    var todayCases: Int?,
    @SerializedName("deaths")
    var deaths: Int?,
    @SerializedName("todayDeaths")
    var todayDeaths: Int?,
    @SerializedName("recovered")
    var recovered: Int?,
    @SerializedName("active")
    var active: Int?,
    @SerializedName("critical")
    var critical: Int?,
    @SerializedName("casesPerOneMillion")
    var casesPerOneMillion: Double?,
    @SerializedName("deathsPerOneMillion")
    var deathsPerOneMillion: Double?,
    @SerializedName("tests")
    var tests: Long?,
    @SerializedName("testsPerOneMillion")
    var testsPerOneMillion: Int?
): RealmObject()