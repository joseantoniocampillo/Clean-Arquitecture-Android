package es.jacampillo.avancedelcovid.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import es.jacampillo.avancedelcovid.database.getDatabase
import es.jacampillo.avancedelcovid.models_api_response.Pais
import es.jacampillo.avancedelcovid.repo.Repositorio
import es.jacampillo.avancedelcovid.toDateFormat
import kotlinx.coroutines.*
import retrofit2.HttpException
import es.jacampillo.avancedelcovid.R

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val paisesRepositorio = Repositorio(getDatabase(application))

    private val _selection = MutableLiveData<Int>()
    private val selection: LiveData<Int> get() = _selection

    private fun obtenerLista(codigo: Int): LiveData<List<Pais>> {
        return when (codigo) {
            R.id.item_actualizar -> paisesRepositorio.listapaises
            R.id.item_ord_fallecidos -> paisesRepositorio.ordenadosFallecidos
            else -> paisesRepositorio.listapaises
        }
    }

    val paisesOrdenados = Transformations.switchMap(selection) {
        // Esta función nos devuelve el liveData que representa paisesOrdenados
        // que a su vez actualiza la vista del recyclerview
        obtenerLista(it)
    }

    val titulo = Transformations.map(paisesOrdenados) {
        it?.let{
            val actualizado = it.get(0).updated?.toDateFormat()
            when (selection.value){
                R.id.item_actualizar -> String.format("%s Ord Positivos", actualizado)
                R.id.item_ord_fallecidos -> String.format("%s Ord Fallecidos", actualizado)
                else -> actualizado
            }
        }
    }

    fun updateSelection(int: Int) {
        _selection.value = int
    }


    init {
        refreshFromRepository()
        _selection.value = R.id.item_actualizar
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
