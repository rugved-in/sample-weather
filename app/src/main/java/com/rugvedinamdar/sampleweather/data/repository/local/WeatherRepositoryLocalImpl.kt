package com.rugvedinamdar.sampleweather.data.repository.local

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rugvedinamdar.sampleweather.data.model.City
import com.rugvedinamdar.sampleweather.util.AppDatabase

class WeatherRepositoryLocalImpl(private val context: Context) : WeatherRepositoryLocal {

    val TAG = WeatherRepositoryLocalImpl::class.simpleName
    private val cityDao: CityDao

    private val _cities: MutableLiveData<List<City>> = MutableLiveData()
    private val _favoriteCities: MutableLiveData<List<City>> = MutableLiveData()

    val cities: LiveData<List<City>>
        get() = _cities
    val favoriteCities: LiveData<List<City>>
        get() = _favoriteCities

    init {
        val database = AppDatabase.getInstance(context)
        cityDao = database.searchCityDao()
    }

    override suspend fun getAll() {
        _cities.postValue(cityDao.getAll())
    }

    override suspend fun loadAllByKeys(keys: IntArray): List<City> {
        return cityDao.loadAllByKeys(keys)
    }

    override suspend fun getAllFavoriteCities() {
        _favoriteCities.postValue(cityDao.getAllFavoriteCities())
    }

    override suspend fun findByName(cityName: String): City {
        return cityDao.findByName(cityName)
    }

    override suspend fun insertAll(vararg cities: City) {
        return cityDao.insertAll(*cities)
    }

    override suspend fun upsertCity(city: City) {
        Log.d(TAG, "upsertCity: $city")
        return cityDao.upsertCity(city)
    }

    override suspend fun delete(city: City) {
        return cityDao.delete(city)
    }

}