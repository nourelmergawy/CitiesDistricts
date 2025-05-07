package com.bosta.citiesdistricts.feature.citiesDistricts.data.mappers

import com.bosta.citiesdistricts.common.data.constans.Constants.INVALID_ID
import com.bosta.citiesdistricts.common.data.mapper.Mapper
import com.bosta.citiesdistricts.feature.citiesDistricts.data.models.dto.DistrictDto
import com.bosta.citiesdistricts.feature.citiesDistricts.data.models.entity.DistrictEntity
import com.bosta.citiesdistricts.feature.citiesDistricts.domain.models.District

internal object DistrictsMapper : Mapper<DistrictDto, District, DistrictEntity>() {

    override fun dtoToDomain(model: DistrictDto): District {
        return District(
            coverage = model.coverage.orEmpty(),
            districtId = model.districtId ?: INVALID_ID,
            districtName = model.districtName.orEmpty(),
            districtOtherName = model.districtOtherName.orEmpty(),
            dropOffAvailability = model.dropOffAvailability ?: false,
            isBusy = model.isBusy ?: false,
            notAllowedBulkyOrders = model.notAllowedBulkyOrders ?: false,
            pickupAvailability = model.pickupAvailability ?: false,
            zoneId = model.zoneId ?: INVALID_ID,
            zoneName = model.zoneName.orEmpty(),
            zoneOtherName = model.zoneOtherName.orEmpty()
        )
    }

    override fun entityToDomain(model: DistrictEntity): District {
        return District(
            coverage = model.coverage,
            districtId = model.districtId,
            districtName = model.districtName,
            districtOtherName = model.districtOtherName,
            dropOffAvailability = model.dropOffAvailability,
            isBusy = model.isBusy,
            notAllowedBulkyOrders = model.notAllowedBulkyOrders,
            pickupAvailability = model.pickupAvailability,
            zoneId = model.zoneId,
            zoneName = model.zoneName,
            zoneOtherName = model.zoneOtherName
        )
    }

    override fun domainToEntity(model: District): DistrictEntity {
        return DistrictEntity(
            coverage = model.coverage,
            districtId = model.districtId,
            districtName = model.districtName,
            districtOtherName = model.districtOtherName,
            dropOffAvailability = model.dropOffAvailability,
            isBusy = model.isBusy,
            notAllowedBulkyOrders = model.notAllowedBulkyOrders,
            pickupAvailability = model.pickupAvailability,
            zoneId = model.zoneId,
            zoneName = model.zoneName,
            zoneOtherName = model.zoneOtherName
        )
    }
}