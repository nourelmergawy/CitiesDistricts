package com.bosta.citiesdistricts.android

import android.app.Application
import com.bosta.citiesdistricts.feature.citiesDistricts.di.AppComponent
import com.bosta.citiesdistricts.feature.citiesDistricts.di.DaggerAppComponent


class BostaApp : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}