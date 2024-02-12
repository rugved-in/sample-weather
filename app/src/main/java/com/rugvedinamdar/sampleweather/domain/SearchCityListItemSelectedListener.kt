package com.rugvedinamdar.sampleweather.domain

import com.rugvedinamdar.sampleweather.data.model.City

interface SearchCityListItemSelectedListener {

    fun onItemSelected(position: Int, city: City)

    fun onFavoriteIconClicked(position: Int, city: City)
}