package es.jacampillo.avancedelcovid.ui.details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.jacampillo.avancedelcovid.database.getDatabase
import es.jacampillo.avancedelcovid.models_api_response.DatosGraph
import es.jacampillo.avancedelcovid.models_api_response.Pais
import es.jacampillo.avancedelcovid.repo.Repositorio
import kotlinx.coroutines.*

class DetailViewModel (pais: Pais, app: Application): ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val paisesRepositorio = Repositorio(getDatabase(app))


    private val pais = pais
    private fun llamadaHistoric(){
        coroutineScope.launch {
            paisesRepositorio.retornaGraficos(pais.country.toLowerCase())
        }
    }

    private val _paisSelected = MutableLiveData<Pais>()
    val paisSelected : LiveData<Pais> get() = _paisSelected

    private val _contenido = MutableLiveData<Array<DatosGraph>>()
    val contenido : LiveData<Array<DatosGraph>> get() = _contenido



    fun rellenaContenido (): Array<DatosGraph> {
        return arrayOf(
            DatosGraph("primero", 1f, -2f),
            DatosGraph("segundo", 2f, 2f),
            DatosGraph("tercero", 3f, 4f)
        )
    }

    init {
        llamadaHistoric()
        _contenido.value = rellenaContenido()
        _paisSelected.value = pais
    }


    class Factory(val pais: Pais,  val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailViewModel(pais, app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
