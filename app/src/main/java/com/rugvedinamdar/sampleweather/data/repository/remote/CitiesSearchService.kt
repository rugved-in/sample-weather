package com.rugvedinamdar.sampleweather.data.repository.remote

import com.rugvedinamdar.sampleweather.data.dto.AutocompleteSearchList
import com.rugvedinamdar.sampleweather.data.dto.CitySearchList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CitiesSearchService {

    @GET(value = "/locations/v1/cities/autocomplete")
    suspend fun autocomplete(
        @Query(value = "apikey") apiKey: String,
        @Query(value = "q") query: String,
        @Query(value = "language") language: String
    ): Response<AutocompleteSearchList>

    @GET(value = "/locations/v1/cities/search")
    suspend fun citySearch(
        @Query(value = "apikey") apiKey: String,
        @Query(value = "q") query: String,
        @Query(value = "language") language: String,
        @Query(value = "details") details: Boolean,
        @Query(value = "offset") offset: Int,
        @Query(value = "alias") alias: String
    ): Response<CitySearchList>
}