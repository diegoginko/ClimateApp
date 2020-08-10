package com.diegoginko.climapp.data

import android.app.Application
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.lang.Exception

class CiudadRepository(){

    private val repositoryJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + repositoryJob)

    companion object{
        private lateinit var ciudadDao : CiudadDao
        private lateinit var aplicacion: Application

        private class GetCiudadesAsyncTask() : AsyncTask<Void, Void, List<Ciudad>>(){
            override fun doInBackground(vararg params: Void?): List<Ciudad> {
                return ciudadDao.getAll()
            }
        }

        private class UpdateCiudadAsyncTask() : AsyncTask<Ciudad, Void, Boolean>(){

            override fun doInBackground(vararg params: Ciudad?): Boolean {
                var retorno = false
                params[0]?.let {
                    if(ciudadDao.update(ciudad = it) != 0){
                        retorno = true
                    }

                }
                return retorno
            }

        }

        private class UpdateCiudadesAsyncTask() : AsyncTask<List<Ciudad>, Void, Boolean>(){

            override fun doInBackground(vararg params: List<Ciudad>?): Boolean {
                var retorno = false
                params[0]?.let {
                    if(ciudadDao.updates(ciudades = it) != 0){
                        retorno = true
                    }

                }
                return retorno
            }

        }

        private class InsertCiudadAsyncTask() : AsyncTask<Ciudad, Void, Boolean>(){
            override fun doInBackground(vararg params: Ciudad?): Boolean {
                var retorno = false
                params[0]?.let {
                    if(ciudadDao.insert(ciudad = it) != 0L){
                        retorno = true
                    }
                }
                return retorno
            }
        }

        private class DeleteAsyncTask() : AsyncTask<Ciudad, Void, Boolean>(){
            override fun doInBackground(vararg params: Ciudad?): Boolean {
                var retorno = false
                params[0]?.let {
                    if(ciudadDao.deleteById(idCiudad = it.ciudadId) != 0){
                        retorno = true
                    }
                }
                return retorno
            }
        }

        private class SetCiudadActualAsyncTask() : AsyncTask<Location?, Void, Boolean>(){

            override fun doInBackground(vararg params: Location?): Boolean {
                var retorno = false
                try {
                    params[0]?.let {
                            location ->
                        var ciudad = ciudadDao.getPrimeraCiudad()
                        ciudad.let {
                            //Paso de coordenadas a ciudad
                            val geocoder = Geocoder(aplicacion)
                            val list = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                            val address = list[0]

                            ciudad.nombre = address.adminArea
                            ciudad.codigoPais = address.countryCode
                            ciudad.localidad = address.locality
                            ciudad.municipio = address.subAdminArea

                            if(ciudadDao.update(ciudad) != 0){
                                retorno = true
                            }
                        }

                    }
                }catch (e: Exception){
                    retorno = false
                }

                return retorno
            }

        }

    }

    constructor(application: Application):this(){
        val database: AppDatabase = AppDatabase.getInstance(application)
        ciudadDao = database.ciudadDao()
        aplicacion = application
    }

    fun getCiudades() : List<Ciudad>{
        return GetCiudadesAsyncTask().execute().get()
    }

    fun update(ciudad: Ciudad) : Boolean{
        return UpdateCiudadAsyncTask().execute(ciudad).get()
    }

    fun updates(ciudades: List<Ciudad>) : Boolean{
        return UpdateCiudadesAsyncTask().execute(ciudades).get()
    }

    fun insert(ciudad: Ciudad) : Boolean{
        return InsertCiudadAsyncTask().execute(ciudad).get()
    }

    fun setCiudadActual(location : Location?) : Boolean{
        return SetCiudadActualAsyncTask().execute(location).get()
    }

    fun delete(ciudad: Ciudad) : Boolean{
        return DeleteAsyncTask().execute(ciudad).get()
    }

}