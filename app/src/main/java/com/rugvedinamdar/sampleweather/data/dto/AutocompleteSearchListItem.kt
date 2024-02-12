package com.rugvedinamdar.sampleweather.data.dto

data class AutocompleteSearchListItem(
    val AdministrativeArea: AdministrativeArea,
    val Country: Country,
    val Key: String,
    val LocalizedName: String,
    val Rank: Int,
    val Type: String,
    val Version: Int
)