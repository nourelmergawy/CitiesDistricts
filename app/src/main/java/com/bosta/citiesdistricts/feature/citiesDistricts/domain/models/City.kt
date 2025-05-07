package com.bosta.citiesdistricts.feature.citiesDistricts.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class City(
    val cityCode: String,
    val cityId: String,
    val cityName: String,
    val cityOtherName: String,
    val districts: List<District>,
    val dropOffAvailability: Boolean,
    val pickupAvailability: Boolean,
) : Parcelable
