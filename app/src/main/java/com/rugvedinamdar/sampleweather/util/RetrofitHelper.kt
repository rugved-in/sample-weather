package com.rugvedinamdar.sampleweather.util

import com.rugvedinamdar.sampleweather.data.repository.remote.CitiesSearchService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    @Volatile
    private var newInstance: CitiesSearchService? = null

    fun getInstance(): CitiesSearchService? {

        synchronized(this) {
            var instance = newInstance

            if (instance == null) {
                val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build()
                instance = retrofit.create(CitiesSearchService::class.java)
                newInstance = instance
            }
            return instance
        }
    }
}