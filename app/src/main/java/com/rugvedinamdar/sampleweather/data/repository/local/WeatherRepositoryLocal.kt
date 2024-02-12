package com.rugvedinamdar.sampleweather.data.repository.local

import com.rugvedinamdar.sampleweather.data.model.City

interface WeatherRepositoryLocal {

    suspend fun getAll()

    suspend fun loadAllByKeys(keys: IntArray): List<City>

    suspend fun getAllFavoriteCities()

    suspend fun findByName(cityName: String): City

    suspend fun insertAll(vararg cities: City)

    suspend fun upsertCity(city: City)

    suspend fun delete(city: City)
}