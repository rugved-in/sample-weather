package com.rugvedinamdar.sampleweather.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rugvedinamdar.sampleweather.data.repository.local.WeatherRepositoryLocalImpl
import com.rugvedinamdar.sampleweather.data.repository.remote.WeatherRepositoryRemote

class WeatherViewModelFactory(
    private val weatherRepositoryRemote: WeatherRepositoryRemote,
    private val implWeatherRepositoryLocal: WeatherRepositoryLocalImpl
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(weatherRepositoryRemote, implWeatherRepositoryLocal) as T
    }
}