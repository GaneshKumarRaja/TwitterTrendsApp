package com.ganesh.twitterapp.data.model

data class CityWeatherModel(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: String,
    val wind: Wind,
    val clouds: Clouds,
    val dt: String,
    val sys: Sys,
    val timezone: String,
    val id: Int,
    val name: String,
    val cod: String
) {
    data class Coord(
            val lon: String,
            val lat: String
    )

    data class Weather(
            val id: Int,
            val main: String,
            val description: String,
            val icon: String
    )

    data class Main(
            val temp: Double,
            val pressure: String,
            val humidity: String,
            val temp_min: Double,
            val temp_max: Double
    )

    data class Wind(
            val speed: String,
            val deg: String
    )

    data class Clouds(
            val all: String
    )

    data class Sys(
            val type: String,
            val id: Int,
            val message: String,
            val country: String,
            val sunrise: String,
            val sunset: String
    )
}