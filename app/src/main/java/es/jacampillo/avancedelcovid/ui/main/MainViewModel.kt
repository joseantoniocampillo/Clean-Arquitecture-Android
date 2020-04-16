package es.jacampillo.avancedelcovid.ui.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import es.jacampillo.avancedelcovid.database.getDatabase
import es.jacampillo.avancedelcovid.models_api_response.Pais
import es.jacampillo.avancedelcovid.repo.Repositorio
import es.jacampillo.avancedelcovid.toDateFormat
import es.jacampillo.avancedelcovid.orden
import es.jacampillo.avancedelcovid.ui.main.MainFragment.Companion.POSITIVOS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(context: Context) : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val paisesRepositorio = Repositorio(getDatabase(context))

    private val _selection = MutableLiveData<Int>()
    val selection: LiveData<Int> get() = _selection

    private fun obtenerLista(codigo: Int): LiveData<List<Pais>> =
        paisesRepositorio.listaSelecionada(codigo)

    val paisesOrdenados = Transformations.switchMap(selection) {
        // Esta función nos devuelve el liveData que representa paisesOrdenados
        // que a su vez actualiza la vista del recyclerview
        Log.d("ttt", selection.value.toString())
        obtenerLista(it)
    }

    val titulo : LiveData<String> = Transformations.map(paisesOrdenados) {
        var control = ""
        it?.let{
            if (it.size > 0) {
                val actualizado = it.get(0).updated?.toDateFormat()
                control = String.format("%s %s", actualizado, orden(selection.value))
            }
        }
        control
    }

    fun updateSelection(int: Int) {
        _selection.value = int
    }


    init {
        refreshFromRepository()
        _selection.value = POSITIVOS
    }

    fun updateVersion(){
        refreshFromRepository()
    }

    private fun refreshFromRepository() {
        coroutineScope.launch {
            paisesRepositorio.refreshPaises() // sacamos del try catch en commit "verifica interntet"
        }
    }

    /*---------------- NAVEGACION -------------------*/

    private val _navegahacia = MutableLiveData<Pais>()
    val navegahacia: LiveData<Pais> get() = _navegahacia

    fun navegahaciaFuncion(pais: Pais){
        _navegahacia.value = pais
    }

    fun navegacionCompletada(){
        _navegahacia.value = null
    }



    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


  /*  Despues de inyección de dependendias con koin no se requiso el Factory nativo para construir
    instancias del Viewmodels con parametros  */

//    class Factory(val context: Context) : ViewModelProvider.Factory {
//        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
//                @Suppress("UNCHECKED_CAST")
//                return MainViewModel(context) as T
//            }
//            throw IllegalArgumentException("Unable to construct viewmodel")
//        }
//    }

//    fun Int.formatoCastellano(int : Int): String{
//        return NumberFormat.getIntegerInstance().format(int)
//    }

}
