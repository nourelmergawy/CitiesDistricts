package com.bosta.citiesdistricts.feature.citiesDistricts.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bosta.citiesdistricts.feature.citiesDistricts.domain.usecases.GetCitiesDistrictsFromRemoteUC
import com.bosta.citiesdistricts.feature.citiesDistricts.presentation.CitiesDistrictsViewModel

class CitiesDistrictsViewModelFactory(
    private val getCitiesDistrictsFromRemoteUC: GetCitiesDistrictsFromRemoteUC
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CitiesDistrictsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CitiesDistrictsViewModel(getCitiesDistrictsFromRemoteUC) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}