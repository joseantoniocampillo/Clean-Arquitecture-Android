package es.jacampillo.avancedelcovid.ui.details

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.jacampillo.avancedelcovid.database.getDatabase
import es.jacampillo.avancedelcovid.models_api_response.Pais
import es.jacampillo.avancedelcovid.models_api_response.historico.PaisHistor
import es.jacampillo.avancedelcovid.repo.Repositorio
import kotlinx.coroutines.*
import java.util.*

class DetailViewModel(val pais: Pais, app: Application) : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val paisesRepositorio = Repositorio(getDatabase(app))

    private val _paisSelected = MutableLiveData<Pais>()
    val paisSelected: LiveData<Pais> get() = _paisSelected

    private val _historical = MutableLiveData<PaisHistor>()
    val historical: LiveData<PaisHistor> get() = _historical

    private fun consigeHistoric() {
        coroutineScope.launch {
            _historical.value = paisesRepositorio.getHistorical(pais.country.toLowerCase(Locale.ROOT))
        }
    }

    init {
        consigeHistoric()
        _paisSelected.value = pais
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(val pais: Pais, val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailViewModel(pais, app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}

