package com.rugvedinamdar.sampleweather.data.repository.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.rugvedinamdar.sampleweather.data.model.City

@Dao
interface CityDao {
    @Query("SELECT * FROM city")
    fun getAll(): List<City>

    @Query("SELECT * FROM city WHERE `key` IN (:keys)")
    fun loadAllByKeys(keys: IntArray): List<City>

    @Query("SELECT * FROM city WHERE is_favorite = 1")
    fun getAllFavoriteCities(): List<City>

    @Query(
        "SELECT * FROM city WHERE name LIKE :cityName LIMIT 1"
    )
    fun findByName(cityName: String): City

    @Insert
    fun insertAll(vararg cities: City)

    @Upsert
    fun upsertCity(city: City)

    @Delete
    fun delete(city: City)
}