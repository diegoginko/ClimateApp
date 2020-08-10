package com.diegoginko.climapp.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.junit.Test

class WeatherApiServiceTest {
    private var testJob = Job()
    private val coroutineScope = CoroutineScope(testJob + Dispatchers.Main )

    @Test
    fun getWeatherTest(){
        coroutineScope.launch {
            var getCiudadesDeferred = WeatherApi.retrofitService.getWeatherCity("Buenos Aires,Buenos Aires,AR")
            try {
                val listResult = getCiudadesDeferred.await()
                assert(listResult.sys.country == "ar")
            }catch (e: Exception){
                assert(false)
            }
        }

    }
}