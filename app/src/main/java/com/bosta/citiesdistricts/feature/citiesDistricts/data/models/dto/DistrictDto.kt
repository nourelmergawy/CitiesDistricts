package com.bosta.citiesdistricts.feature.citiesDistricts.data.models.dto

import com.google.gson.annotations.SerializedName

data class DistrictDto(
    @SerializedName("coverage")
    val coverage: String? = null,
    @SerializedName("districtId")
    val districtId: String? = null,
    @SerializedName("districtName")
    val districtName: String? = null,
    @SerializedName("districtOtherName")
    val districtOtherName: String? = null,
    @SerializedName("dropOffAvailability")
    val dropOffAvailability: Boolean? = null,
    @SerializedName("isBusy")
    val isBusy: Boolean? = null,
    @SerializedName("notAllowedBulkyOrders")
    val notAllowedBulkyOrders: Boolean? = null,
    @SerializedName("pickupAvailability")
    val pickupAvailability: Boolean? = null,
    @SerializedName("zoneId")
    val zoneId: String? = null,
    @SerializedName("zoneName")
    val zoneName: String? = null,
    @SerializedName("zoneOtherName")
    val zoneOtherName: String? = null,
)