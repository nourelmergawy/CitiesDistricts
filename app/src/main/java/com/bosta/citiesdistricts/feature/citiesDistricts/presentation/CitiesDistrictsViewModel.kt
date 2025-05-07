package com.bosta.citiesdistricts.feature.citiesDistricts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bosta.citiesdistricts.common.data.models.state.Resource
import com.bosta.citiesdistricts.feature.citiesDistricts.domain.models.City
import com.bosta.citiesdistricts.feature.citiesDistricts.domain.usecases.GetCitiesDistrictsFromRemoteUC
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CitiesDistrictsViewModel(
    private val getCitiesDistrictsFromRemoteUC: GetCitiesDistrictsFromRemoteUC
) : ViewModel() {

    private val _state = MutableStateFlow<Resource<List<City>>>(
        Resource.Loading()
    )
    val state: StateFlow<Resource<List<City>>> = _state.asStateFlow()
    private val _allCities = MutableStateFlow<List<City>>(emptyList())
    private val _searchQuery = MutableStateFlow("")

    val filteredCities: StateFlow<List<City>> = combine(_allCities, _searchQuery) { cities, query ->
        if (query.isBlank()) cities
        else cities.filter { it.cityName.contains(query, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        fetchCitiesAndDistricts()
    }

    fun fetchCitiesAndDistricts() {
        viewModelScope.launch {
            getCitiesDistrictsFromRemoteUC().collect { resource ->
                _state.value = resource
                if (resource is Resource.Success) {
                    _allCities.value = resource.data
                }
            }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

}