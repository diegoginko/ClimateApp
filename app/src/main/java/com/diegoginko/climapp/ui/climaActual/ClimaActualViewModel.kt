package com.diegoginko.climapp.ui.climaActual

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.diegoginko.climapp.data.Ciudad
import com.diegoginko.climapp.data.CiudadRepository
import com.diegoginko.climapp.network.Extendido
import com.diegoginko.climapp.network.WeatherApi
import com.diegoginko.climapp.network.WeatherApiService
import com.diegoginko.climapp.network.WeatherResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

enum class WeatherApiStatus { LOADING, ERROR, DONE }

class ClimaActualViewModel(

    val ciudadRepository : CiudadRepository,
    application: Application) : AndroidViewModel(application){

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    //var listCiudades = ciudadRepository.getCiudades()

    private val _listCiudades = MutableLiveData<List<Ciudad>>()
    val listCiudades: LiveData<List<Ciudad>>
        get() = _listCiudades

    private val _status = MutableLiveData<WeatherApiStatus>()
    val status: LiveData<WeatherApiStatus>
        get() = _status

    private val _properties = MutableLiveData<WeatherResponse>()
    val properties: LiveData<WeatherResponse>
        get() = _properties

    private var _showToastEvent = MutableLiveData<Boolean>()

    val showToastEvent: LiveData<Boolean>
        get() = _showToastEvent

    fun doneShowingToast() {
        _showToastEvent.value = false
    }

    private var _toastEventText = MutableLiveData<String>()

    val toastEventText: LiveData<String>
        get() = _toastEventText

    private var _showAddCiudadDialog = MutableLiveData<Boolean>()

    val showAddCiudadDialog: LiveData<Boolean>
        get() = _showAddCiudadDialog

    fun doneShowingCiudadDialog() {
        _showAddCiudadDialog.value = false
    }

    private var _closeDialogCiudad = MutableLiveData<Boolean>()

    val closeDialogCiudad : LiveData<Boolean>
        get() = _closeDialogCiudad

    fun doneCloseDialogCiudad(){
        _closeDialogCiudad.value = false
    }

    private val _navigateToPronosticoExtendido = MutableLiveData<Ciudad>()

    val navigateToPronosticoExtendido: LiveData<Ciudad>
        get() = _navigateToPronosticoExtendido

    fun displayPronosticoExtendido(ciudad: Ciudad) {
        _navigateToPronosticoExtendido.value = ciudad
    }

    fun displayPronosticoExtendidoComplete() {
        _navigateToPronosticoExtendido.value = null
    }


    init {
        getCiudades()
        //updateClimaCiudades()
        //getClimaCiudad("Gerli,B1824,ar")
        //_listCiudades.value = ciudadRepository.getCiudades()
    }

    fun getCiudades(){
        coroutineScope.launch {
            var ciudades = ciudadRepository.getCiudades()
            //Recursivo para la primera vez hasta que crea la ciudad actual
            if(!ciudades.isEmpty()){
                ciudades.forEach {
                    var getCiudadesDeferred = WeatherApi.retrofitService.getWeatherCity(it.nombre.trim()+","+it.nombre.trim()+","+it.codigoPais.trim())
                    try {
                        _status.value = WeatherApiStatus.LOADING

                        val listResult = getCiudadesDeferred.await()

                        it.temp = listResult.main.temp
                        it.tempMin = listResult.main.temp_min
                        it.tempMax = listResult.main.temp_max
                        it.sensTemp = listResult.main.feels_like
                        it.descripcionClima = listResult.weather[0].description
                        it.iconoClima = listResult.weather[0].icon

                        //ciudadRepository.update(it)
                    }catch (e: Exception) {
                        Timber.e(e)
                        _status.value = WeatherApiStatus.ERROR
                        _toastEventText.value = "Problema al traer la ciudad: " + it.nombre.trim()
                        _showToastEvent.value = true
                    }
                }
                ciudadRepository.updates(ciudades)
                ciudades = ciudadRepository.getCiudades()
                _listCiudades.value = ciudades
            }else{
                //Trata de traer hasta que se cargue la primer ciudad
                getCiudades()
            }
        }
    }

//    fun updateClimaCiudades(){
//        var ciudades = ciudadRepository.getCiudades()
//        ciudades.forEach {
//            getClimaCiudad(it)
//        }
//        //Las traigo con las modificaciones
//        ciudades = ciudadRepository.getCiudades()
//        _listCiudades.value = ciudades
//    }

    fun nuevaCiudad(codigoPais : String, nombreCiudad : String){
        if(ciudadRepository.insert(Ciudad(0L,nombreCiudad.trim(),codigoPais.trim()))){
            //updateClimaCiudades()
            getCiudades()
        }else{
            _toastEventText.value = "Problema al guardar la ciudad: " + nombreCiudad.trim()
            _showToastEvent.value = true
        }
        //Cierra el Dialog
        _closeDialogCiudad.value = true
    }

//    private fun getClimaCiudad(ciudad: Ciudad){
//        coroutineScope.launch {
//            var getCiudadesDeferred = WeatherApi.retrofitService.getWeatherCity(ciudad.nombre.trim()+","+ciudad.nombre.trim()+","+ciudad.codigoPais.trim())
//            try {
//                _status.value = WeatherApiStatus.LOADING
//
//                val listResult = getCiudadesDeferred.await()
//
//                ciudad.temp = listResult.main.temp
//                ciudad.tempMin = listResult.main.temp_min
//                ciudad.tempMax = listResult.main.temp_max
//                ciudad.sensTemp = listResult.main.feels_like
//                ciudad.descripcionClima = listResult.weather[0].description
//                ciudad.iconoClima = listResult.weather[0].icon
//
//                ciudadRepository.update(ciudad)
////                _status.value = WeatherApiStatus.DONE
////                _properties.value = listResult
//
//            }catch (e: Exception) {
////                _status.value = WeatherApiStatus.ERROR
//                //_properties.value = WeatherResponse()
//                Timber.e(e)
//                _toastEventText.value = "Problema al traer la ciudad: " + ciudad.nombre.trim()
//                _showToastEvent.value = true
//            }
//
//        }
//    }

    fun onAddCiudadClicked(){
        if(_listCiudades.value!!.size < 6){
            _showAddCiudadDialog.value = true
        }else{
            _toastEventText.value = "No se pueden agregar mas de 5 ciudades."
            _showToastEvent.value = true
        }
    }


    fun onCiudadClicked(ciudad : Ciudad){
        _navigateToPronosticoExtendido.value = ciudad
    }

    fun onCiudadLongClicked(ciudad : Ciudad){
        if(ciudad.ciudadId != 1L){
            if(ciudadRepository.delete(ciudad)){
                _toastEventText.value = "Ciudad borrada"
                _showToastEvent.value = true
                getCiudades()
            }else{
                _toastEventText.value = "No se pudo borrar la ciudad seleccionada"
                _showToastEvent.value = true
            }
        }else{
            _toastEventText.value = "No se puede borrar la ubicacion actual"
            _showToastEvent.value = true
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}