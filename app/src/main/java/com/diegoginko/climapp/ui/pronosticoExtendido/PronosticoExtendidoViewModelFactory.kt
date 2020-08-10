package com.diegoginko.climapp.ui.pronosticoExtendido

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.diegoginko.climapp.data.Ciudad

class PronosticoExtendidoViewModelFactory (
    private val ciudad: Ciudad,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PronosticoExtendidoViewModel::class.java)) {
            return PronosticoExtendidoViewModel(ciudad, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}