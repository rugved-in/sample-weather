package com.rugvedinamdar.sampleweather.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rugvedinamdar.sampleweather.data.dto.AutocompleteSearchList
import com.rugvedinamdar.sampleweather.data.dto.CitySearchList
import com.rugvedinamdar.sampleweather.data.model.City
import com.rugvedinamdar.sampleweather.data.repository.local.WeatherRepositoryLocalImpl
import com.rugvedinamdar.sampleweather.data.repository.remote.WeatherRepositoryRemote

class WeatherViewModel(
    private val repository: WeatherRepositoryRemote,
    private val repositoryLocal: WeatherRepositoryLocalImpl
) : ViewModel() {


    val autocompleteSearchList: LiveData<AutocompleteSearchList>
        get() = repository.autocompleteSearchList

    val citySearchList: LiveData<CitySearchList>
        get() = repository.citySearchList

    val favoriteCities: LiveData<List<City>>
        get() = repositoryLocal.favoriteCities

}