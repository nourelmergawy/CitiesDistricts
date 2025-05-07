package com.bosta.citiesdistricts.feature.citiesDistricts.di

import androidx.lifecycle.ViewModelProvider
import com.bosta.citiesdistricts.common.domain.repository.remote.IRestApiNetworkProvider
import com.bosta.citiesdistricts.feature.citiesDistricts.data.models.repository.CitiesDistrictsRepository
import com.bosta.citiesdistricts.feature.citiesDistricts.data.models.repository.remote.CitiesDistrictsRemoteDS
import com.bosta.citiesdistricts.feature.citiesDistricts.domain.repository.ICitiesDistrictsRepository
import com.bosta.citiesdistricts.feature.citiesDistricts.domain.repository.remote.ICitiesDistrictsRemoteDS
import com.bosta.citiesdistricts.feature.citiesDistricts.domain.usecases.GetCitiesDistrictsFromRemoteUC
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CitiesDistrictsModule {
    @Provides
    @Singleton
    fun provideCitiesDistrictsRemoteDS(
        restApiNetworkProvider: IRestApiNetworkProvider
    ): ICitiesDistrictsRemoteDS {
        return CitiesDistrictsRemoteDS(restApiNetworkProvider)
    }

    @Provides
    @Singleton
    fun provideCitiesDistrictsRepository(
        remoteDS: ICitiesDistrictsRemoteDS
    ): ICitiesDistrictsRepository {
        return CitiesDistrictsRepository(remoteDS)
    }

    @Provides
    @Singleton
    fun provideGetCitiesDistrictsFromRemoteUC(
        repository: ICitiesDistrictsRepository
    ): GetCitiesDistrictsFromRemoteUC {
        return GetCitiesDistrictsFromRemoteUC(repository)
    }

    @Provides
    fun provideCitiesDistrictsViewModelFactory(
        useCase: GetCitiesDistrictsFromRemoteUC
    ): ViewModelProvider.Factory {
        return CitiesDistrictsViewModelFactory(useCase)
    }
}