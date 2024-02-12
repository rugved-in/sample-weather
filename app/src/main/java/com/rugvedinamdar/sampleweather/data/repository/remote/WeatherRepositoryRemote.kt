package com.rugvedinamdar.sampleweather.data.repository.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rugvedinamdar.sampleweather.data.dto.AutocompleteSearchList
import com.rugvedinamdar.sampleweather.data.dto.CitySearchList
import com.rugvedinamdar.sampleweather.util.Constants
import com.rugvedinamdar.sampleweather.util.Constants.CITY_SEARCH_DEFAULT_OFFSET
import com.rugvedinamdar.sampleweather.util.Constants.SEARCH_ALIAS_NEVER

class WeatherRepositoryRemote(private val citiesSearchService: CitiesSearchService?) {

    val TAG = WeatherRepositoryRemote::class.simpleName

    private val _autocompleteSearchList: MutableLiveData<AutocompleteSearchList> = MutableLiveData()
    private val _citySearchList: MutableLiveData<CitySearchList> = MutableLiveData()

    val autocompleteSearchList: LiveData<AutocompleteSearchList>
        get() = _autocompleteSearchList
    val citySearchList: LiveData<CitySearchList>
        get() = _citySearchList

    suspend fun getAutocompleteSearchCities(query: String) {
        val result = citiesSearchService?.autocomplete(
            Constants.API_KEY,
            query,
            Constants.API_LANGUAGE_ENGLISH
        )
        if (result?.body() != null) {
            _autocompleteSearchList.postValue(result.body())
            Log.d(TAG, "getAutocompleteSearchCities size: ${result.body()!!.size}")
        }
    }

    suspend fun getCitySearch(query: String) {
        val result = citiesSearchService?.citySearch(
            Constants.API_KEY,
            query,
            Constants.API_LANGUAGE_ENGLISH,
            false,
            CITY_SEARCH_DEFAULT_OFFSET,
            SEARCH_ALIAS_NEVER
        )
        if (result?.body() != null) {
            _citySearchList.postValue(result.body())
            Log.d(TAG, "getCitySearch size: ${result.body()!!.size}")
        }
    }
}