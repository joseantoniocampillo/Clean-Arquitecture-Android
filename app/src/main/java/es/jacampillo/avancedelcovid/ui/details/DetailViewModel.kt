package es.jacampillo.avancedelcovid.ui.details

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.jacampillo.avancedelcovid.database.getDatabase
import es.jacampillo.avancedelcovid.models_api_response.DatosGraph
import es.jacampillo.avancedelcovid.models_api_response.Pais
import es.jacampillo.avancedelcovid.models_api_response.historico.PaisHistor
import es.jacampillo.avancedelcovid.repo.Repositorio
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class DetailViewModel (val pais: Pais, app: Application): ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val paisesRepositorio = Repositorio(getDatabase(app))

    private val _paisSelected = MutableLiveData<Pais>()
    val paisSelected : LiveData<Pais> get() = _paisSelected

    private val _fallecidos = MutableLiveData<ArrayList<DatosGraph>>()
    val fallecidos : LiveData<ArrayList<DatosGraph>> get() = _fallecidos

    private val _recuperados = MutableLiveData<ArrayList<DatosGraph>>()
    val recuperados : LiveData<ArrayList<DatosGraph>> get() = _recuperados

    private val _casos = MutableLiveData<ArrayList<DatosGraph>>()
    val casos : LiveData<ArrayList<DatosGraph>> get() = _casos



    private fun consigeHistoric(){
        coroutineScope.launch {
            try {
            val historico = paisesRepositorio.getHistorical(pais.country.toLowerCase(Locale.ROOT)).await()
                _fallecidos.value = datosGraphDeHistorico(historico, Grafica.FALLECIDOS)
                _casos.value = datosGraphDeHistorico(historico, Grafica.CASOS)
                _recuperados.value = datosGraphDeHistorico(historico, Grafica.RECUPERADOS)
            }catch (e: Exception){
                Log.d("aaa:a", "error: ${e.localizedMessage}" )
            }
        }
    }

    fun datosGraphDeHistorico(histor: PaisHistor, grafica: Grafica): ArrayList<DatosGraph> {
        val timeline = histor.timeline
        var control = 1f
        val datosGraph: ArrayList<DatosGraph> = ArrayList()
        val lista  = when(grafica) {
            Grafica.FALLECIDOS -> timeline.deaths.lista
            Grafica.CASOS -> timeline.cases.lista
            Grafica.RECUPERADOS -> timeline.recovered.lista
        }
        for (item in lista){
//            datosGraph.add(DatosGraph(item.key, item.key.split("/")[1].toFloat(), item.value.toFloat()))
            datosGraph.add(DatosGraph(item.key, control, item.value.toFloat()))
            control++
        }
        return datosGraph
    }

    init {
        consigeHistoric()
//        _contenido.value = rellenaContenido()
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

//    fun rellenaContenido (): Array<DatosGraph> {
//        return arrayOf(
//            DatosGraph("primero", 1f, -2f),
//            DatosGraph("segundo", 2f, 2f),
//            DatosGraph("tercero", 3f, 4f)
//        )
//    }
//
//    private fun llamadaHistoric(){
//        coroutineScope.launch {
//            paisesRepositorio.retornaGraficos(pais.country.toLowerCase(Locale.ROOT))
//        }
//    }
}

enum class Grafica {FALLECIDOS, CASOS, RECUPERADOS}
