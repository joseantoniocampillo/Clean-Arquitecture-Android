package es.jacampillo.avancedelcovid.model
import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import es.jacampillo.avancedelcovid.model.CountryInfo

@Keep
data class Pais(
    @SerializedName("active")
    var active: Int?,
    @SerializedName("cases")
    var cases: Int?,
    @SerializedName("casesPerOneMillion")
    var casesPerOneMillion: Double?,
    @SerializedName("country")
    var country: String?,
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
    var updated: Long?
)