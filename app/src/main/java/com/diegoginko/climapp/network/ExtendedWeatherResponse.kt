package com.diegoginko.climapp.network

data class ExtendedWeatherResponse(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<Extendido>,
    val city: City

)

data class Extendido(
    val dt: Long,
    val main: Temperatura,
    val weather: List<Clima>,
    val clouds: Nubes,
    val wind: Viento,
    val dt_txt: String
)

data class City(
    val id: Int,
    val name: String,
    val coord: Coordenadas,
    val country: String,
    val timezone: Int,
    val sunrise: Int,
    val sunset: Int
)