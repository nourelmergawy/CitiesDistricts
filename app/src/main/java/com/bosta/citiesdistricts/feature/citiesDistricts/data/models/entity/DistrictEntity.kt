package com.bosta.citiesdistricts.feature.citiesDistricts.data.models.entity

data class DistrictEntity(
    val coverage: String,
    val districtId: String,
    val districtName: String,
    val districtOtherName: String,
    val dropOffAvailability: Boolean,
    val isBusy: Boolean,
    val notAllowedBulkyOrders: Boolean,
    val pickupAvailability: Boolean,
    val zoneId: String,
    val zoneName: String,
    val zoneOtherName: String
)
