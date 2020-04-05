package es.jacampillo.avancedelcovid.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import es.jacampillo.avancedelcovid.database.getDatabase
import es.jacampillo.avancedelcovid.models_api_response.Pais
import es.jacampillo.avancedelcovid.network.PaisesApi
import es.jacampillo.avancedelcovid.repo.Repositorio
import kotlinx.coroutines.*
import retrofit2.HttpException


class MainViewModel (application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _paises = MutableLiveData<List<Pais>>()
//    val paises : LiveData<List<Pais>> get() = _paises

    private val paisesRepositorio = Repositorio(getDatabase(application))
    val paises = paisesRepositorio.listapaises

    init {
        //networkCall()
        refreshFromRepository()
    }

    private fun refreshFromRepository(){
        coroutineScope.launch {
            try {
                paisesRepositorio.refreshPaises()
            } catch (e: HttpException){
                Log.d("error_ttt", e.message())
            }
        }
    }

    private fun networkCall(){
        coroutineScope.launch {
            val paisesDeferred = PaisesApi.retrofitService.getCountries()
            try {
                val lista = paisesDeferred.await()
                _paises.value = lista
            } catch (e: HttpException){
                Log.d("error_ttt", e.message())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}
