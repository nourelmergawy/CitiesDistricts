package com.bosta.citiesdistricts.feature.citiesDistricts.domain.repository.remote

import com.bosta.citiesdistricts.feature.citiesDistricts.data.models.dto.CitiesDistrictsResponseDto

interface ICitiesDistrictsRemoteDS {
    suspend fun getCitiesDistricts(): CitiesDistrictsResponseDto
}