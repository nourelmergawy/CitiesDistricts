package com.bosta.citiesdistricts.feature.citiesDistricts.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class District(
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

) : Parcelable
