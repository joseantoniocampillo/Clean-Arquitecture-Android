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
import es.jacampillo.avancedelcovid.ui.main.MainFragment.Companion.FALLECIDOS
import es.jacampillo.avancedelcovid.ui.main.MainFragment.Companion.FALLECIDOS_HOY
import es.jacampillo.avancedelcovid.ui.main.MainFragment.Companion.GRAVES
import es.jacampillo.avancedelcovid.ui.main.MainFragment.Companion.POSITIVOS
import es.jacampillo.avancedelcovid.ui.main.MainFragment.Companion.RECUPERADOS
import es.jacampillo.avancedelcovid.ui.main.MainFragment.Companion.TEST
import es.jacampillo.avancedelcovid.ui.main.MainFragment.Companion.TEST_POR_MILLON

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val paisesRepositorio = Repositorio(getDatabase(application))

    private val _selection = MutableLiveData<Int>()
    private val selection: LiveData<Int> get() = _selection

    private fun obtenerLista(codigo: Int): LiveData<List<Pais>> =
        paisesRepositorio.listaSelecionada(codigo)

    val paisesOrdenados = Transformations.switchMap(selection) {
        // Esta función nos devuelve el liveData que representa paisesOrdenados
        // que a su vez actualiza la vista del recyclerview
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

    fun orden(codigo: Int?): String {
        return when (codigo) {
            FALLECIDOS -> "👓 Fallecidos"
            POSITIVOS -> "\uD83D\uDC53 Positivos"
            FALLECIDOS_HOY -> "🎚 Hoy"
            RECUPERADOS -> "\uD83D\uDC53 ♥ Recuperados"
            GRAVES -> "💊 Graves"
            TEST -> "🧪 Test"
            TEST_POR_MILLON -> "\uD83E\uDDEA Test por 1M"
            else -> " "
        }
    }

}
