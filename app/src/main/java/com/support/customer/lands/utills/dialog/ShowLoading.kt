package com.support.customer.lands.utills.dialog

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.support.customer.lands.R

object ShowLoading {
    private var materialDialog: MaterialDialog? = null

    fun show(pContext: Context?) {
        if (materialDialog?.isShowing != true){
            pContext?.let {
                materialDialog = MaterialDialog.Builder(it)
                    .content(R.string.text_processing)
                    .progress(true, 0)
                    .canceledOnTouchOutside(false)
                    .cancelable(false)
                    .show()
            }
        }
    }

    fun dismiss() {
        materialDialog?.let {
            if (it.isShowing)
                it.dismiss()
        }
    }
}