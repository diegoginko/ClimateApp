package com.diegoginko.climapp.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.openweathermap.org/"
const val API_KEY = "AQUI_TU_API_KEY"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface WeatherApiService {
    @GET("data/2.5/weather")
    fun getWeatherCity(
        @Query("q") ciudad: String,
        @Query("appid") appid: String = API_KEY,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "sp"):
            Deferred<WeatherResponse>

    @GET("data/2.5/forecast")
    fun getWeatherCityExtended(
        @Query("q") ciudad: String,
        @Query("appid") appid: String = API_KEY,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "sp"):
            Deferred<ExtendedWeatherResponse>
}

object WeatherApi{
    val retrofitService : WeatherApiService by lazy { retrofit.create(WeatherApiService::class.java) }
}