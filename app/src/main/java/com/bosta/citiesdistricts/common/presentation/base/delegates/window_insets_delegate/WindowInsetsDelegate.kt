package com.bosta.citiesdistricts.common.presentation.base.delegates.window_insets_delegate

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WindowInsetsDelegate : IWindowInsetsDelegate {
    override fun applyInsetsForStatusBar(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top
            v.setPadding(0, statusBarInsets, 0, 0)
            insets
        }
    }

    override fun applyInsetsForSystemBars(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}