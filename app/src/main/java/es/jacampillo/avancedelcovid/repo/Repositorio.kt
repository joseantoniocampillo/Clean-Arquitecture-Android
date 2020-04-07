package es.jacampillo.avancedelcovid.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import es.jacampillo.avancedelcovid.database.PaisesDatabase
import es.jacampillo.avancedelcovid.database.asDatabasePaises
import es.jacampillo.avancedelcovid.database.asPaisDomain
import es.jacampillo.avancedelcovid.models_api_response.Pais
import es.jacampillo.avancedelcovid.network.PaisesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repositorio (private val database: PaisesDatabase) {

    suspend fun refreshPaises(){
        withContext(Dispatchers.IO){
            try {
                val listapaises = PaisesApi.retrofitService.getCountries().await()
                database.paisesDao.insertPaises(listapaises.asDatabasePaises())
            } catch (e: Exception){
                Log.d("error_ttt_repositorio", e.localizedMessage?: e.toString())
            }
        }
    }

    fun listaSelecionada(int: Int) = Transformations
        .map(database.paisesDao.getFromDatabase(int)){
            it.asPaisDomain()
        }
}