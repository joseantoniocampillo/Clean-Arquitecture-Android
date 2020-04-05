package es.jacampillo.avancedelcovid.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import es.jacampillo.avancedelcovid.models_api_response.CountryInfo
import es.jacampillo.avancedelcovid.models_api_response.Pais

@Entity
class DatabasePais constructor(
    @PrimaryKey
    @SerializedName("country")
    var country: String,

    /*En este caso solo necesitamos un string (la url de la bandera del pais)*/
    @SerializedName("countryInfo")
    var countryInfo: String?,

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
    @SerializedName("active")
    var active: Int?,
    @SerializedName("cases")
    var cases: Int?,
    @SerializedName("casesPerOneMillion")
    var casesPerOneMillion: Double?
)

fun List<DatabasePais>.asPaisDomain(): List<Pais>{
    return map {
        Pais(
            country = it.country,
            countryInfo = CountryInfo(flag = it.countryInfo),
            critical = it.critical,
            deaths = it.deaths,
            deathsPerOneMillion = it.deathsPerOneMillion,
            recovered = it.recovered,
            todayCases = it.todayCases,
            todayDeaths = it.todayDeaths,
            updated = it.updated,
            active = it.active,
            cases = it.cases,
            casesPerOneMillion = it.casesPerOneMillion
        )
    }
}

fun List<Pais>.asDatabasePaises(): List<DatabasePais>{
    return map {
        DatabasePais(
            country = it.country,
            countryInfo = it.countryInfo?.flag,
            critical = it.critical,
            deaths = it.deaths,
            deathsPerOneMillion = it.deathsPerOneMillion,
            recovered = it.recovered,
            todayCases = it.todayCases,
            todayDeaths = it.todayDeaths,
            updated = it.updated,
            active = it.active,
            cases = it.cases,
            casesPerOneMillion = it.casesPerOneMillion
        )
    }
}