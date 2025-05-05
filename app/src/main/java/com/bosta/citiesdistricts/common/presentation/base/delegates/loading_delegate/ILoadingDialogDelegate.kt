package com.bosta.citiesdistricts.common.presentation.base.delegates.loading_delegate

interface ILoadingDialogDelegate {
    fun showLoading(isLoading: Boolean, message: String? = null)
}