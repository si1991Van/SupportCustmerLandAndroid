package com.support.customer.lands.fcm

import android.databinding.BaseObservable
import com.support.customer.lands.services.api.OauthApi
import android.provider.Settings.Secure



class FcmViewModel : BaseObservable() {

    var token: String? = null
    val deviceName = android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL
    var deviceId :String? = null



    fun updateNotificationToken(onSuccess : (String?) ->Unit, onFailed : (String?) -> Unit) {
        OauthApi().updateNotificationToken(1.toString(), deviceName, deviceId, token, onResponse = {
            if (it.isSuccess()) {
                onSuccess(it.responseContent)
            } else {
                onFailed(it.getErrorMessage())
            }
        })
    }


}