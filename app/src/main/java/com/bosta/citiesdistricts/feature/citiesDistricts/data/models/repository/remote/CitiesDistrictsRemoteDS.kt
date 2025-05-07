package com.bosta.citiesdistricts.feature.citiesDistricts.data.models.repository.remote

import com.bosta.citiesdistricts.common.domain.repository.remote.IRestApiNetworkProvider
import com.bosta.citiesdistricts.feature.citiesDistricts.data.models.dto.CitiesDistrictsResponseDto
import com.bosta.citiesdistricts.feature.citiesDistricts.domain.repository.remote.ICitiesDistrictsRemoteDS

class CitiesDistrictsRemoteDS(private val restApiNetworkProvider: IRestApiNetworkProvider) :
    ICitiesDistrictsRemoteDS {
    override suspend fun getCitiesDistricts(): CitiesDistrictsResponseDto {
        return restApiNetworkProvider.get(
            pathUrl = "cities/getAllDistricts",
            queryParams = mapOf("countryId" to "60e4482c7cb7d4bc4849c4d5"),
            responseType = CitiesDistrictsResponseDto::class.java
        )
    }

}