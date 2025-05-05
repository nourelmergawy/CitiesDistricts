package com.bosta.citiesdistricts.common.presentation.base.delegates.message_delegate

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

interface IMessageDelegate {
    fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT)
    fun showSnackbar(
        view: View,
        message: String,
        duration: Int = Snackbar.LENGTH_SHORT,
        actionText: String? = null,
        action: (() -> Unit)? = null
    )

    fun showDialog(
        context: Context,
        title: String? = null,
        message: String,
        positiveButtonText: String? = null,
        negativeButtonText: String? = null,
        onPositiveClick: (() -> Unit)? = null,
        onNegativeClick: (() -> Unit)? = null
    )
}