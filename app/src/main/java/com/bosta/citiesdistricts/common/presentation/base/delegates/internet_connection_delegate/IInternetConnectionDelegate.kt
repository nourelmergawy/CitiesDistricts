package com.bosta.citiesdistricts.common.presentation.base.delegates.internet_connection_delegate

import android.content.Context

interface IInternetConnectionDelegate {
    fun isNetworkAvailable(context: Context): Boolean
}