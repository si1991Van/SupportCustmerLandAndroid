package com.support.customer.lands.viewmodel

import android.app.Activity
import android.text.TextUtils
import com.support.customer.lands.R
import com.support.customer.lands.model.BaseResponse
import com.support.customer.lands.services.GsonUtil
import com.support.customer.lands.services.api.OauthApi
import com.support.customer.lands.utills.dialog.ShowAlert

class ChangeViewModel {
    var odlPassword : String? = null
    var newPassword : String? = null
    var newConfirmPassword : String? = null

    fun changePassword(onSuccess : (String?) ->Unit, onFailed : (String?) -> Unit){
       OauthApi().changePassword(odlPassword, newPassword, newConfirmPassword, onResponse = {
           var response = GsonUtil.fromJson(it.responseContent, BaseResponse::class.java)
           if (it.isSuccess()){
               onSuccess(response?.message)
           }else{
               onFailed(it.getErrorMessage())
           }
       })
    }


    fun validate(activity : Activity) : Boolean?{
        var result = true
        if (TextUtils.isEmpty(odlPassword)) {
            ShowAlert.fail(pContext = activity, message = activity.getString(R.string.text_validate_require_field_old_password))
            result = false
        }else if (TextUtils.isEmpty(newPassword)){
            ShowAlert.fail(pContext = activity, message = activity.getString(R.string.text_validate_require_field_new_password))
            result = false
        }else if (TextUtils.isEmpty(newPassword)){
            ShowAlert.fail(pContext = activity, message = activity.getString(R.string.text_validate_require_field_new_confirm_password))
            result = false
        }else if (newPassword != newConfirmPassword) {
            ShowAlert.fail(pContext = activity, message = activity.getString(R.string.change_password_error_new_password_not_equal))
            result = false
        }
        return result
    }
}