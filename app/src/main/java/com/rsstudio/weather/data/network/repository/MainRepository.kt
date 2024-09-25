package com.rsstudio.weather.data.network.repository

import com.rsstudio.weather.data.network.apis.WeatherApiInterface
import com.rsstudio.weather.data.network.model.Weather
import retrofit2.Response
import javax.inject.Inject

class MainRepository
@Inject
constructor(private val api: WeatherApiInterface) {

    suspend fun getWeatherData(latitude: Double, longitude: Double): Response<Weather> {
        return api.getWeatherData(latitude,longitude)
    }

}