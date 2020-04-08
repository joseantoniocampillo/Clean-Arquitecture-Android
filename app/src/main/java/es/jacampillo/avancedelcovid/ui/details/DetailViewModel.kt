package es.jacampillo.avancedelcovid.ui.details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.jacampillo.avancedelcovid.models_api_response.Pais

class DetailViewModel (pais: Pais, app: Application): ViewModel() {
    // TODO: Implement the ViewModel


    private val _paisSelected = MutableLiveData<Pais>()
    val paisSelected : LiveData<Pais> get() = _paisSelected

    init {
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


    // de momento no uso todo !!
    override fun onCleared() {
        super.onCleared()
    }

}
