package com.diegoginko.climapp.ui.pronosticoExtendido

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.diegoginko.climapp.data.Ciudad
import com.diegoginko.climapp.network.Extendido
import com.diegoginko.climapp.network.WeatherApi
import com.diegoginko.climapp.ui.climaActual.WeatherApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PronosticoExtendidoViewModel(val ciudad: Ciudad, application: Application) : AndroidViewModel(application){

    private val _ciudadSeleccionada = MutableLiveData<Ciudad>()

    val ciudadSeleccionada: LiveData<Ciudad>
        get() = _ciudadSeleccionada

    private val _listaPronostico = MutableLiveData<List<Extendido>>()
    val listaPronostico: LiveData<List<Extendido>>
        get() = _listaPronostico

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    init {
        _ciudadSeleccionada.value = ciudad
        getClimaExtendidoCiudad(ciudad)
    }

    private fun getClimaExtendidoCiudad(ciudad: Ciudad){
        coroutineScope.launch {
            var getPropertiesDeferred = WeatherApi.retrofitService.getWeatherCityExtended(ciudad.nombre)
            try {
                val listResult = getPropertiesDeferred.await()
                //TODO: solo mostrar las de distintos dias (5 en total).
                _listaPronostico.value = listResult.list

            }catch (e: Exception) {

            }

        }
    }

    fun onExtendidoClicked(extendido: Extendido){

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}