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


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val paisesRepositorio = Repositorio(getDatabase(application))

    private val _entrada = MutableLiveData<Int>()
    private val entrada : LiveData<Int> get() = _entrada

    private fun obtenerLista(codigo: Int): LiveData<List<Pais>>{
        return when (codigo) {
            1 -> paisesRepositorio.listapaises
            2 -> paisesRepositorio.ordenadosFallecidos
            else -> paisesRepositorio.listapaises
        }
    }

    val paisesOrdenados = Transformations.switchMap(entrada){
        obtenerLista(it)
    }

    fun listanormal(){
        _entrada.value = 1
    }
    fun lista2(){
        _entrada.value = 2
    }


    init {
        refreshFromRepository()
        _entrada.value = 1
        Log.d("ttt_init", paisesOrdenados.value?.get(0)?.country ?: "null ")
    }

    private fun refreshFromRepository() {
        coroutineScope.launch {
            try {
                paisesRepositorio.refreshPaises()
            } catch (e: HttpException) {
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
