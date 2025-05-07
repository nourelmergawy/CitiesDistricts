package com.bosta.citiesdistricts.feature.citiesDistricts.data.models.entity

data class CityEntity(
    val cityCode: String,
    val cityId: String,
    val cityName: String,
    val cityOtherName: String,
    val districtsEntity: List<DistrictEntity>,
    val dropOffAvailability: Boolean,
    val pickupAvailability: Boolean,
)
