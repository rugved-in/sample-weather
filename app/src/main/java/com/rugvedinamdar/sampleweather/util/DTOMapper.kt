package com.rugvedinamdar.sampleweather.util

import com.rugvedinamdar.sampleweather.data.dto.CitySearchList
import com.rugvedinamdar.sampleweather.data.dto.CitySearchListItem
import com.rugvedinamdar.sampleweather.data.model.City

object DTOMapper {

    fun citySearchListItemToSearchCity(citySearchListItem: CitySearchListItem): City {
        return City(
            citySearchListItem.Key,
            citySearchListItem.EnglishName,
            citySearchListItem.Country.EnglishName,
            citySearchListItem.AdministrativeArea.EnglishName,
            citySearchListItem.TimeZone.Code,
            citySearchListItem.TimeZone.Name,
            false
        )
    }

    fun citySearchListToSearchCityList(citySearchList: CitySearchList): List<City> {
        val searchCities: ArrayList<City> = ArrayList()
        for (cityItem in citySearchList) {
            searchCities.add(citySearchListItemToSearchCity(cityItem))
        }
        return searchCities
    }
}