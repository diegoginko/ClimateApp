package com.diegoginko.climapp.ui.climaActual

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.diegoginko.climapp.data.CiudadRepository

class ClimaActualViewModelFactory (
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClimaActualViewModel::class.java)) {
            val ciudadRepository : CiudadRepository = CiudadRepository(application)
            return ClimaActualViewModel(ciudadRepository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}