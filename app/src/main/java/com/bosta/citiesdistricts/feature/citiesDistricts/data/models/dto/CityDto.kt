package com.bosta.citiesdistricts.feature.citiesDistricts.data.models.dto

import com.google.gson.annotations.SerializedName

data class CityDto(
    @SerializedName("cityCode")
    val cityCode: String? = null,
    @SerializedName("cityId")
    val cityId: String? = null,
    @SerializedName("cityName")
    val cityName: String? = null,
    @SerializedName("cityOtherName")
    val cityOtherName: String? = null,
    @SerializedName("districts")
    val districts: List<DistrictDto>? = null,
    @SerializedName("pickupAvailability")
    val dropOffAvailability: Boolean? = null,
    @SerializedName("dropOffAvailability")
    val pickupAvailability: Boolean? = null
)