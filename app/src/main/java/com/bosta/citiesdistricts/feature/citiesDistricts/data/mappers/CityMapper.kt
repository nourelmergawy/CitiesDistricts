package com.bosta.citiesdistricts.feature.citiesDistricts.data.mappers

import com.bosta.citiesdistricts.common.data.constans.Constants.INVALID_CITY_CODE
import com.bosta.citiesdistricts.common.data.constans.Constants.INVALID_ID
import com.bosta.citiesdistricts.common.data.mapper.Mapper
import com.bosta.citiesdistricts.feature.citiesDistricts.data.models.dto.CityDto
import com.bosta.citiesdistricts.feature.citiesDistricts.data.models.entity.CityEntity
import com.bosta.citiesdistricts.feature.citiesDistricts.domain.models.City

internal object CityMapper : Mapper<CityDto, City, CityEntity>() {
    override fun dtoToDomain(model: CityDto): City {

        return City(
            cityCode = model.cityCode ?: INVALID_CITY_CODE,
            cityId = model.cityId ?: INVALID_ID,
            cityName = model.cityName.orEmpty(),
            cityOtherName = model.cityOtherName.orEmpty(),
            districts = model.districts?.map { DistrictsMapper.dtoToDomain(it) }!!,
            dropOffAvailability = model.dropOffAvailability ?: false,
            pickupAvailability = model.pickupAvailability ?: false,
        )
    }

    override fun entityToDomain(model: CityEntity): City {
        return City(
            cityCode = model.cityCode,
            cityId = model.cityId,
            cityName = model.cityName,
            cityOtherName = model.cityOtherName,
            districts = model.districtsEntity.map { DistrictsMapper.entityToDomain(it) },
            dropOffAvailability = model.dropOffAvailability,
            pickupAvailability = model.pickupAvailability,
        )
    }

    override fun domainToEntity(model: City): CityEntity {
        return CityEntity(
            cityCode = model.cityCode,
            cityId = model.cityId,
            cityName = model.cityName,
            cityOtherName = model.cityOtherName,
            districtsEntity = model.districts.map { DistrictsMapper.domainToEntity(it) },
            dropOffAvailability = model.dropOffAvailability,
            pickupAvailability = model.pickupAvailability,

            )
    }

    fun dtoListToDomain(dtoList: List<CityDto>): List<City> {
        return dtoList.map {
            dtoToDomain(it)
        }
    }
}