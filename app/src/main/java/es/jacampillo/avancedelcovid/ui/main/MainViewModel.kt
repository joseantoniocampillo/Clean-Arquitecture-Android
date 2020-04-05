package es.jacampillo.avancedelcovid.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.jacampillo.avancedelcovid.model.Pais
import es.jacampillo.avancedelcovid.network.PaisesApi
import kotlinx.coroutines.*
import retrofit2.HttpException


class MainViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _paises = MutableLiveData<List<Pais>>()
    val paises : LiveData<List<Pais>> get() = _paises

    init {
        networkCall()
    }

    private fun networkCall(){
        coroutineScope.launch {
            val paisesDeferred = PaisesApi.retrofitService.getCountries()
            try {
                val lista = paisesDeferred.await()
                _paises.value = lista
            } catch (e: HttpException){
                //Timber.d("ttt_ ") todo()
                Log.e("error_ttt", e.localizedMessage)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
