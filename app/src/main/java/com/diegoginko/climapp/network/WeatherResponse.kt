package com.diegoginko.climapp.network


data class WeatherResponse(
    val coord: Coordenadas,
    val weather: List<Clima>,
    val base : String,
    val main : Temperatura,
    val visibility : Int,
    val wind : Viento,
    val clouds : Nubes,
    val dt : Int,
    val sys: Sol,
    val timezone : Int,
    val id : Int,
    val name : String,
    val cod : Int

)


data class Coordenadas(
    val lon: Double,
    val lat: Double
)

data class Clima(
    val id : Int,
    val main : String,
    val description : String,
    val icon : String
)

data class Temperatura(
    val temp : Double,
    val feels_like : Double,
    val temp_min : Double,
    val temp_max : Double,
    val pressure : Int,
    val humidity : Int
)

data class Viento(
    val speed : Double,
    val deg: Int
)

data class Nubes(
    val all : Int
)

data class Sol(
//    val type : Int,
//    val id : Int,
    val country : String,
    val sunrise : Int,
    val sunset : Int
)
