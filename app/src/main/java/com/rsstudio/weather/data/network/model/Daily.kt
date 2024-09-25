package com.rsstudio.weather.data.network.model

data class Daily(
    val temperature_2m_max: List<Double>,
    val time: List<String>,
    val sunrise: List<String>,
    val sunset: List<String>,
    val weathercode: List<Int>
)