package es.jacampillo.avancedelcovid.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.google.gson.Gson
import es.jacampillo.avancedelcovid.database.PaisesDatabase
import es.jacampillo.avancedelcovid.database.asDatabasePaises
import es.jacampillo.avancedelcovid.database.asPaisDomain
import es.jacampillo.avancedelcovid.models_api_response.Pais
import es.jacampillo.avancedelcovid.models_api_response.historico.PaisHistor
import es.jacampillo.avancedelcovid.network.PaisesApi
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.HttpException

class Repositorio(private val database: PaisesDatabase) {


    fun getHistorical(paisId: String): Deferred<PaisHistor> {
        return PaisesApi.retrofitService.getDataGraficos(paisId)
    }

    suspend fun retornaGraficos(paisId: String) {
        withContext(Dispatchers.IO) {
            try {
                val deferedPaisHistorical =
                    PaisesApi.retrofitService.getDataGraficos(paisId).await()
                val deaths = deferedPaisHistorical.timeline.deaths
                var f = deaths.lista
                f.forEach {
                    Log.d("xxx_3", "deaths "    + it.key + it.value.toString())
                }
            } catch (e: HttpException) {

            }
        }
    }

    suspend fun refreshPaises() {
        withContext(Dispatchers.IO) {
            try {
                val listapaises = PaisesApi.retrofitService.getCountries().await()
                database.paisesDao.insertPaises(listapaises.asDatabasePaises())
            } catch (e: Exception) {
                Log.d("error_ttt_repositorio", e.localizedMessage ?: e.toString())
            }
        }
    }

    fun listaSelecionada(int: Int) = Transformations
        .map(database.paisesDao.getFromDatabase(int)) {
            it.asPaisDomain()
        }
}

