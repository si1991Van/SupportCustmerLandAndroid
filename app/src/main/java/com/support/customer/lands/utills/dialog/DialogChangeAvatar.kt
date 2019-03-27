package com.support.customer.lands.utills.dialog

import android.app.Activity
import android.app.Dialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.support.customer.lands.R
import com.support.customer.lands.databinding.DialogChangeProfileImageBinding

class DialogChangeAvatar(activity: Activity?, var onSelectedCamera : ()-> Unit, var onSelectedGallery:()-> Unit) : Dialog(activity) {

    private lateinit var bindingChangeImage : DialogChangeProfileImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingChangeImage = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_change_profile_image, null, false)
        setContentView(bindingChangeImage.root)
        bindingChangeImage.rippleCamera.setOnRippleCompleteListener {
            onSelectedCamera()
            dismiss()
        }
        bindingChangeImage.rippleImageLibrary.setOnRippleCompleteListener {
            onSelectedGallery()
            dismiss()
        }
        bindingChangeImage.rippleCancel.setOnRippleCompleteListener {
            dismiss()
        }

    }

}