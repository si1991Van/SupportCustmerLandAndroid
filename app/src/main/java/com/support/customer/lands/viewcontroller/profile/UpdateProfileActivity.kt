package com.support.customer.lands.viewcontroller.profile

import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import com.support.customer.lands.R
import com.support.customer.lands.databinding.ActivityUpdateProfileBinding
import com.support.customer.lands.services.UserServices
import com.support.customer.lands.utills.CommonUtil
import com.support.customer.lands.utills.IntentActionKeys
import com.support.customer.lands.utills.dialog.ShowAlert
import com.support.customer.lands.utills.dialog.ShowLoading
import com.support.customer.lands.utills.extensions.hideSoftKeyboard
import com.support.customer.lands.viewmodel.ChangeViewModel
import com.support.customer.lands.viewmodel.ProfileViewModel
import haiphat.com.bds.nghetuvan.view.BaseActivity

class UpdateProfileActivity : BaseActivity() {

    private lateinit var updateProfileActivity: ActivityUpdateProfileBinding
    private var vm = ProfileViewModel()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getContentView(): View {

        updateProfileActivity = DataBindingUtil.inflate(layoutInflater, R.layout.activity_update_profile, null, false)
        updateProfileActivity.btnSave.setOnClickListener {

            vm.fullName = updateProfileActivity.telFullName.text.toString()
            vm.email = updateProfileActivity.telEmail.text.toString()
            vm.birthday = updateProfileActivity.telBirthday.text.toString()
            ShowLoading.show(this@UpdateProfileActivity)
            vm.updateProfile(onSuccess = {
                ShowLoading.dismiss()
                ShowAlert.fail(pContext = this, dialogTitle = getString(R.string.alert_title_inform), message = it, onClick = {
                    setResult(IntentActionKeys.RELOAD_DATA)
                    finish()
                })
            }, onFailed = {
                ShowLoading.dismiss()
                ShowAlert.fail(pContext = this, message = it)
            })
        }
        initView()
        return updateProfileActivity.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHeaderTitle(getString(R.string.txt_update_information_title))
    }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initView(){
        UserServices?.userInfo?.let {
            updateProfileActivity.telFullName.setText(it.fullname)
            updateProfileActivity.telEmail.setText(it.email)
            updateProfileActivity.telBirthday.setText(it.birthday)
        }
        updateProfileActivity.telBirthday.isFocusable = true
        updateProfileActivity.telBirthday.showSoftInputOnFocus = false

        updateProfileActivity.telBirthday.setOnClickListener {
            CommonUtil.showDatePickerDialog(this@UpdateProfileActivity, updateProfileActivity.telBirthday)
        }
    }
}
