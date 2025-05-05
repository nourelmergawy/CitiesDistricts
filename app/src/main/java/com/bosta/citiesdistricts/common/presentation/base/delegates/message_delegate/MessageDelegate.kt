package com.bosta.citiesdistricts.common.presentation.base.delegates.message_delegate

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar


class MessageDelegate : IMessageDelegate {
    override fun showToast(context: Context, message: String, duration: Int) {
        Toast.makeText(context, message, duration).show()
    }

    override fun showSnackbar(
        view: View,
        message: String,
        duration: Int,
        actionText: String?,
        action: (() -> Unit)?,
    ) {
        val snackbar = Snackbar.make(view, message, duration)
        if (actionText != null && action != null) {
            snackbar.setAction(actionText) {
                action()
            }
        }
        snackbar.show()
    }


    override fun showDialog(
        context: Context,
        title: String?,
        message: String,
        positiveButtonText: String?,
        negativeButtonText: String?,
        onPositiveClick: (() -> Unit)?,
        onNegativeClick: (() -> Unit)?
    ) {
        AlertDialog.Builder(context).apply {
            title?.let { setTitle(it) }
            setMessage(message)
            positiveButtonText?.let { text ->
                setPositiveButton(text) { _, _ ->
                    onPositiveClick?.invoke()
                }
            } ?: run {
                setPositiveButton("Ok") { _, _ -> }
            }

            negativeButtonText?.let { text ->
                setNegativeButton(text) { _, _ -> onNegativeClick?.invoke() }
            }
        }.show()
    }
}