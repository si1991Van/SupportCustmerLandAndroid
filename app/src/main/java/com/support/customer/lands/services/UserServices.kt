package com.support.customer.lands.services

import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.text.TextUtils
import com.support.customer.lands.BaseApplication
import com.support.customer.lands.model.UserResponse
import com.support.customer.lands.utills.IntentActionKeys
import com.support.customer.lands.utills.SharePreferencesKeys
import com.support.customer.lands.utills.SharePreferencesLoaders

object UserServices {
    var accessToken: String? = null
    var userInfo: UserResponse? = null

    init {
        this.accessToken = SharePreferencesLoaders.getValue(SharePreferencesKeys.ACCESS_TOKEN, null)
        var strJson = SharePreferencesLoaders.getValue(SharePreferencesKeys.USER_INFO, null)
        strJson?.let {
            userInfo = GsonUtil.fromJson(strJson, UserResponse::class.java)
        }
    }

    fun saveUserInfo(response: UserResponse?) {
        this.accessToken = response?.token
        SharePreferencesLoaders.saveValue(SharePreferencesKeys.ACCESS_TOKEN, this.accessToken)
        this.userInfo = response
        SharePreferencesLoaders.saveValue(SharePreferencesKeys.USER_INFO, userInfo?.let { GsonUtil.toJson(it) })
    }

    fun saveProfile(response: UserResponse?) {
       this.userInfo = response
        SharePreferencesLoaders.saveValue(SharePreferencesKeys.USER_INFO, userInfo?.let { GsonUtil.toJson(it) })
    }

    fun isLoggedIn(): Boolean {
        return !TextUtils.isEmpty(this.accessToken)
    }

    fun logout() {
        this.userInfo = null
        this.accessToken = null
        SharePreferencesLoaders.clear()
        val pIntent = Intent()
        pIntent.action = IntentActionKeys.FORCE_LOGOUT_ACTION
        LocalBroadcastManager.getInstance(BaseApplication.context).sendBroadcast(pIntent)
    }
}