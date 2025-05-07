package com.bosta.citiesdistricts.feature.citiesDistricts.domain.usecases

import com.bosta.citiesdistricts.common.data.models.exception.BostaException
import com.bosta.citiesdistricts.common.data.models.state.Resource
import com.bosta.citiesdistricts.feature.citiesDistricts.domain.models.City
import com.bosta.citiesdistricts.feature.citiesDistricts.domain.repository.ICitiesDistrictsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class GetCitiesDistrictsFromRemoteUC(
    private val citiesDistrictsRepository: ICitiesDistrictsRepository
) {
    operator fun invoke(): Flow<Resource<List<City>>> = flow {
        emit(Resource.Loading())
        val city = citiesDistrictsRepository.getCitiesDistrictsFromRemoteUC()
        emit(Resource.Success(city))
    }.catch { throwable ->
        val exception =
            when (throwable) {
                is BostaException -> throwable
                is UnknownHostException -> BostaException.Network.UnknownHost(errorMessage = throwable.localizedMessage)
                is SocketTimeoutException -> BostaException.Network.Timeout(errorMessage = throwable.localizedMessage)
                else -> BostaException.UnknownException("Unknown error in GetCitiesDistrictsFromRemoteUC: $throwable")
            }
        emit(Resource.Failure(exception))

    }.onCompletion {
        emit(Resource.Loading(false))
    }.flowOn(Dispatchers.IO)
}