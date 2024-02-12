package com.rugvedinamdar.sampleweather.util

import com.rugvedinamdar.sampleweather.BuildConfig

object Constants {

    const val BASE_URL = "https://dataservice.accuweather.com"
    const val API_KEY = BuildConfig.API_KEY
    val API_LANGUAGE_ENGLISH = "en-us"
    const val SEARCH_ALIAS_NEVER = "Never"
    const val SEARCH_ALIAS_ALWAYS = "Always"
    const val CITY_SEARCH_DEFAULT_OFFSET = 50
    const val LOCATION_KEY = "location_key"
    const val DB_NAME = "SampleWeatherDb"
}