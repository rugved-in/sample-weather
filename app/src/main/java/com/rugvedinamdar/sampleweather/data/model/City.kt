package com.rugvedinamdar.sampleweather.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class City(
    @PrimaryKey
    val key: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "country_name")
    val countryName: String,
    @ColumnInfo(name = "administrative_area_name")
    val administrativeAreaName: String,
    @ColumnInfo(name = "timezone_code")
    val timeZoneCode: String,
    @ColumnInfo(name = "timezone_code_name")
    val timeZoneName: String,
    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean


) {
    override fun toString(): String {
        return "City(key='$key', name='$name', isFavorite=$isFavorite)"
    }
}
