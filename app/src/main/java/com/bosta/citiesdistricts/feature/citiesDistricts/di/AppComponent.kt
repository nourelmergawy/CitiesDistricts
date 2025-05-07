package com.bosta.citiesdistricts.feature.citiesDistricts.di

import android.app.Application
import com.bosta.citiesdistricts.common.di.NetworkModule
import com.bosta.citiesdistricts.feature.citiesDistricts.presentation.CitiesDistrictsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, CitiesDistrictsModule::class])
@Singleton
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

    fun inject(fragment: CitiesDistrictsFragment)
}