package com.rsstudio.weather.data.network.model

data class Weather(
    val daily: Daily,
    val hourly: Hourly,
    val timezone: String,
)