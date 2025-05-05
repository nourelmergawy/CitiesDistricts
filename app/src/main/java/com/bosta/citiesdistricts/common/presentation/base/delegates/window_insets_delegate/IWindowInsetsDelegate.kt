package com.bosta.citiesdistricts.common.presentation.base.delegates.window_insets_delegate

import android.view.View

interface IWindowInsetsDelegate {
    fun applyInsetsForStatusBar(view: View)
    fun applyInsetsForSystemBars(view: View)
}