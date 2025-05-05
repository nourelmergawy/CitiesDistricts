package com.bosta.citiesdistricts.common.presentation.base.delegates.exception_delegate

import com.bosta.citiesdistricts.common.data.models.exception.BostaException

interface BostaExceptionDelegate {
    fun handleBostaException(
        exception: BostaException,
        onRetry: (() -> Unit)
    )
}