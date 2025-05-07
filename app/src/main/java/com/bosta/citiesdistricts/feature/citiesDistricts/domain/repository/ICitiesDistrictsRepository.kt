package com.bosta.citiesdistricts.feature.citiesDistricts.domain.repository

import com.bosta.citiesdistricts.feature.citiesDistricts.domain.models.City

interface ICitiesDistrictsRepository {
    suspend fun getCitiesDistrictsFromRemoteUC(): List<City>
}