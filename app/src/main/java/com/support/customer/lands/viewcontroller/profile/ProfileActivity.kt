package com.support.customer.lands.viewcontroller.profile

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import com.support.customer.lands.R
import com.support.customer.lands.databinding.ActivityProfileBinding
import com.support.customer.lands.utills.CommonUtil
import haiphat.com.bds.nghetuvan.view.BaseActivity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.design.widget.BottomSheetDialog
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.Window
import android.widget.Toast
import com.soundcloud.android.crop.Crop
import com.support.customer.lands.BaseApplication.Companion.context
import com.support.customer.lands.fcm.NotificationService
import com.support.customer.lands.services.UserServices
import com.support.customer.lands.utills.IntentActionKeys
import com.support.customer.lands.utills.dialog.DialogChangeAvatar
import com.support.customer.lands.utills.dialog.ShowAlert
import com.support.customer.lands.utills.dialog.ShowLoading
import com.support.customer.lands.utills.extensions.fromUrl
import com.support.customer.lands.viewmodel.ProfileViewModel
import haiphat.com.bds.nghetuvan.view.isSelectorProfile
import java.io.File


class ProfileActivity : BaseActivity() {
    private lateinit var profileBinding: ActivityProfileBinding
    private var vm = ProfileViewModel()


    @RequiresApi(Build.VERSION_CODES.M)
    override fun getContentView(): View {
        profileBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_profile, null, false)
        initView()

        return profileBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHeaderTitle(getString(R.string.txt_title_profile))


    }

    private fun updateView() {
        profileBinding.imgAvatar.fromUrl(
            url = UserServices?.userInfo?.avatar,
            placeHolder = R.drawable.ic_defaut_avatar
        )
        profileBinding.txtName.text = UserServices?.userInfo?.fullname
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initView() {
        updateView()
        profileBinding.lnUser.setOnClickListener {
            showDialogUpdateAvatar()

        }

        profileBinding.ripUpdateInformation.setOnRippleCompleteListener {
            startActivityForResult(
                Intent(this@ProfileActivity, UpdateProfileActivity::class.java),
                IntentActionKeys.UPDATE_INFORMATION
            )
        }
        profileBinding.ripChangePassword.setOnRippleCompleteListener {
            startActivity(Intent(this@ProfileActivity, ChangePasswordActivity::class.java))
        }
        profileBinding.ripBooked.setOnRippleCompleteListener {
            startActivity(Intent(this@ProfileActivity, BookedActivity::class.java))
        }
        profileBinding.ripSetting.setOnRippleCompleteListener {
            startActivity(Intent(this@ProfileActivity, SettingActivity::class.java))
        }
        profileBinding.ripShare.setOnRippleCompleteListener {
            Log.e("Tag: ", "https://play.google.com/store/apps/details?id=$packageName")
            CommonUtil.shareAppLinkViaFacebook(
                this@ProfileActivity,
                "https://play.google.com/store/apps/details?id=$packageName"
            )
        }
        profileBinding.ripRatting.setOnRippleCompleteListener {

           try {
               startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
           }catch (e: Exception){
               ShowAlert.fail(pContext = this, message = getString(R.string.text_cannot_fail_ratting))
           }

        }
        profileBinding.ripSendEmail.setOnRippleCompleteListener {
            CommonUtil.clickSendMail(this@ProfileActivity, "sivannguyen1991@gmail.com")
        }
        profileBinding.ripInfo.setOnRippleCompleteListener {

            var dialog = BottomSheetDialog(this)
            dialog.setContentView(R.layout.dialog_info_app)
            dialog.show()

        }
        profileBinding.txtSignOut.setOnClickListener {
            ShowAlert.confirm(this@ProfileActivity, message = getString(R.string.profile_logout_confirm), onClick = {
                vm.logout(Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID), NotificationService.fcmViewModel.token, onSuccess = {
                    UserServices.logout()
                }, onFailed = {
                    ShowAlert.fail(pContext = this@ProfileActivity, message = it)
                })


            })
        }
    }


    private fun changeAvatar(pathTofFile: String){
        ShowLoading.show(this@ProfileActivity)
        vm.updateAvatar(pathTofFile, onSuccess = {
            ShowLoading.dismiss()
            updateView()
            baseActivityBinding.imgRight.fromUrl(url = UserServices?.userInfo?.avatar, placeHolder = R.drawable.ic_defaut_avatar)
        }, onFailed = {
            ShowAlert.fail(pContext = context, message = it)
            ShowLoading.dismiss()
        })
    }


    private fun handleCrop(resultCode: Int, result: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            changeAvatar(Crop.getOutput(result).path)
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this@ProfileActivity, getString(R.string.text_error), Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (CommonUtil.requestPermissionGrantResults(grantResults)) {
            when (requestCode) {
                IntentActionKeys.REQUEST_CAMERA_PERMISSION -> {
                    openCamera()
                }
                IntentActionKeys.REQUEST_SELECT_FILE_PERMISSION -> {
                    openGallery()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IntentActionKeys.UPDATE_INFORMATION -> {
                if (resultCode == IntentActionKeys.RELOAD_DATA) {
                    updateView()
                }
            }
            isSelectorProfile.SELECT_FILE -> {
                if (resultCode == Activity.RESULT_OK) {
                    beginCrop(data?.data)
                }
            }
            isSelectorProfile.REQUEST_CAMERA -> {
                if (resultCode == Activity.RESULT_OK) {
                    beginCrop(imageUri)
                }
            }
            Crop.REQUEST_CROP -> {
                handleCrop(resultCode, data)
            }
        }

    }


}
