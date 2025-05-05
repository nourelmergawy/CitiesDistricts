package com.bosta.citiesdistricts.common.presentation.base.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.bosta.citiesdistricts.common.presentation.base.delegates.window_insets_delegate.IWindowInsetsDelegate
import com.bosta.citiesdistricts.common.presentation.base.delegates.window_insets_delegate.WindowInsetsDelegate


abstract class BaseActivity<VB : ViewBinding>(private val inflateMethod: (LayoutInflater) -> VB) :
    AppCompatActivity(), IWindowInsetsDelegate by WindowInsetsDelegate() {
    private lateinit var _binding: VB
    protected val binding: VB
        get() = _binding

    abstract fun setupUI()
    abstract fun onActivityCreated(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = inflateMethod.invoke(layoutInflater)
        setContentView(_binding.root)
        setupUI()
        onActivityCreated(savedInstanceState)
    }
}