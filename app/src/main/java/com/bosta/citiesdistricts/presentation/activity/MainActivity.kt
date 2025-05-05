package com.bosta.citiesdistricts.presentation.activity

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.bosta.citiesdistricts.R
import com.bosta.citiesdistricts.common.presentation.base.activity.BaseActivity
import com.bosta.citiesdistricts.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override fun setupUI() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

    }
}