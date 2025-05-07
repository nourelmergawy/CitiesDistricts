package com.bosta.citiesdistricts.feature.citiesDistricts.data.models.dto

import com.google.gson.annotations.SerializedName

data class CitiesDistrictsResponseDto(
    @SerializedName("data")
    val cities: List<CityDto>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean,
)