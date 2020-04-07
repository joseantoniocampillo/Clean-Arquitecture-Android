package es.jacampillo.avancedelcovid.models_api_response

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

/*
* Clase generada con el plugin de Android Studio
* que genera un kotlin file (con un data class)
* Al encontror otro json dentro jenera automáticamente
* otro archivo para este nuevo json (interior).
* Obviamente le da el nombre que encuentra en este caso en inglés CountryInfo
*/

@Keep
data class Pais(
    @SerializedName("active")
    var active: Int?,
    @SerializedName("cases")
    var cases: Int?,
    @SerializedName("casesPerOneMillion")
    var casesPerOneMillion: Double?,
    @SerializedName("country")
    var country: String,
    @SerializedName("countryInfo")
    var countryInfo: CountryInfo?,
    @SerializedName("critical")
    var critical: Int?,
    @SerializedName("deaths")
    var deaths: Int?,
    @SerializedName("deathsPerOneMillion")
    var deathsPerOneMillion: Double?,
    @SerializedName("recovered")
    var recovered: Int?,
    @SerializedName("todayCases")
    var todayCases: Int?,
    @SerializedName("todayDeaths")
    var todayDeaths: Int?,
    @SerializedName("updated")
    var updated: Long?,
    @SerializedName("tests")
    var tests: Long?,
    @SerializedName("testsPerOneMillion")
    var testsPerOneMillion: Int?


)

//tests: 2011529,
//testsPerOneMillion: 6077