package es.jacampillo.avancedelcovid.db_realm

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*
//import es.jacampillo.avancedelcovid.models_api_response.Pais

open class PaisRealm(
    var updated: Long? = null,
    var country: String? = null,
    var countryInfo: String? = null,
    var cases: Int? = null,
    var todayCases: Int? = null,
    var deaths: Int? = null,
    var todayDeaths: Int? = null,
    var recovered: Int? = null,
    var active: Int? = null,
    var critical: Int? = null,
    var casesPerOneMillion: Double? = null,
    var deathsPerOneMillion: Int? = null,
    var tests: Long? = null,
    var testsPerOneMillion: Int? = null
): RealmObject()

open class SortedPais(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var paises: RealmList<PaisRealm> = RealmList()
): RealmObject()

/*
fun List<Pais>.asDatabasePaises(): List<PaisRealm>{
    return map {
        PaisRealm(
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
            casesPerOneMillion = it.casesPerOneMillion,
            tests = it.tests,
            testsPerOneMillion = it.testsPerOneMillion
        )
    }
}*/
