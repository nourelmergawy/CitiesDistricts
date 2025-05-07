package com.bosta.citiesdistricts.feature.citiesDistricts.data.models.repository

import com.bosta.citiesdistricts.feature.citiesDistricts.data.mappers.CityMapper
import com.bosta.citiesdistricts.feature.citiesDistricts.domain.models.City
import com.bosta.citiesdistricts.feature.citiesDistricts.domain.repository.ICitiesDistrictsRepository
import com.bosta.citiesdistricts.feature.citiesDistricts.domain.repository.remote.ICitiesDistrictsRemoteDS

class CitiesDistrictsRepository(private val remoteDS: ICitiesDistrictsRemoteDS) :
    ICitiesDistrictsRepository {
    override suspend fun getCitiesDistrictsFromRemoteUC(): List<City> {
        val result = remoteDS.getCitiesDistricts()
        val cites = CityMapper.dtoListToDomain(result.cities)
        return cites
    }
}