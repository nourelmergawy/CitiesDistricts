package com.bosta.citiesdistricts.common.presentation.base.delegates.loading_delegate

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.bosta.citiesdistricts.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoadingDialogDelegate(private val context: Context) : ILoadingDialogDelegate {
    private var loadingDialog: AlertDialog? = null

    override fun showLoading(isLoading: Boolean, message: String?) {
        if (isLoading) {
            loadingDialog?.show() ?: run {
                val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)
                loadingDialog = MaterialAlertDialogBuilder(context)
                    .setView(dialogView)
                    .setCancelable(false)
                    .create()
                loadingDialog?.findViewById<TextView>(R.id.tv_loadingMessage)?.text = message

                // Set an OnDismissListener to release resources when the dialog is dismissed
                loadingDialog?.setOnDismissListener {
                    loadingDialog = null
                }

                loadingDialog?.show()
            }
        } else {
            loadingDialog?.dismiss()
        }
    }

}