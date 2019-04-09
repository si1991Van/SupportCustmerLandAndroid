package com.support.customer.lands.viewcontroller.home

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import com.soundcloud.android.crop.Crop
import com.support.customer.lands.BaseApplication
import com.support.customer.lands.BaseApplication.Companion.context
import com.support.customer.lands.R
import com.support.customer.lands.adapter.CreateManagerAdapter
import com.support.customer.lands.adapter.ProjectAdapter
import com.support.customer.lands.databinding.ActivityCreateManagerBinding
import com.support.customer.lands.model.*
import com.support.customer.lands.services.GsonUtil
import com.support.customer.lands.services.UserServices
import com.support.customer.lands.utills.CommonUtil
import com.support.customer.lands.utills.IntentActionKeys
import com.support.customer.lands.utills.IntentDataKeys
import com.support.customer.lands.utills.dialog.DialogChangeAvatar
import com.support.customer.lands.utills.dialog.ShowAlert
import com.support.customer.lands.utills.dialog.ShowLoading
import com.support.customer.lands.utills.extensions.fromUrl
import com.support.customer.lands.utills.validation.Validator
import com.support.customer.lands.viewcontroller.profile.ProfileActivity
import com.support.customer.lands.viewmodel.CreateManagerViewModel
import haiphat.com.bds.nghetuvan.view.BaseActivity
import haiphat.com.bds.nghetuvan.view.isSelectorProfile
import java.io.File
import java.text.FieldPosition


class CreateManagerActivity : BaseActivity() {

    lateinit var createManagerActivity: ActivityCreateManagerBinding
    var vm = CreateManagerViewModel()
    var list = ArrayList<LinkResponse>()
//    var list = ArrayList<String>()



    override fun getContentView(): View {
        createManagerActivity = DataBindingUtil.inflate(layoutInflater, R.layout.activity_create_manager, null, false)

        createManagerActivity.btnSave.setOnClickListener {
            if (vm.type == IntentActionKeys.CREATE_MANAGER) {
                addManager()
            } else if (vm.type == IntentActionKeys.UPDATE_MANAGER) {
                update()
            }
        }
        getOption()
        createManagerActivity.clUploadImage.setOnClickListener {
            showDialogUpdateAvatar()
        }
        return createManagerActivity.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHeaderTitle(getString(R.string.txt_profile_massage))

    }

    private fun initView(response: OptionResponse?) {
        var bundle = intent.extras
        vm.type = bundle?.getInt(IntentDataKeys.KEY_CREATE_MANAGER_TYPE)
        UserServices?.userInfo?.let {
            createManagerActivity.telFullName.setText(it.fullname)
            createManagerActivity.telPhone.setText(it.phone)
            createManagerActivity.telEmail.setText(it.email)
        }
        if (vm.type == 0) {
            vm.type = bundle?.getInt(IntentDataKeys.KEY_UPDATE_MANAGER_TYPE)
            vm.managerResponse =
                    GsonUtil.fromJson(bundle?.getString(IntentDataKeys.KEY_MANAGER), ManagerResponse::class.java)
            createManagerActivity.telFullName.setText(vm?.managerResponse?.fullname)
            createManagerActivity.telPhone.setText(vm?.managerResponse?.phone)
            createManagerActivity.telEmail.setText(vm?.managerResponse?.email)
            createManagerActivity.telTilte.setText(vm?.managerResponse?.title)
            createManagerActivity.telAddress.setText(vm?.managerResponse?.address)
            createManagerActivity.telArea.setText(vm?.managerResponse?.area.toString())
            createManagerActivity.telPrice.setText(vm?.managerResponse?.price.toString())
            createManagerActivity.editDescription.setText(vm?.managerResponse?.description)

            setValueUpdateSpinner(createManagerActivity.spinner6, response?.price_type, vm.managerResponse?.price_type)
            setValueUpdateSpinner(createManagerActivity.spinner5, response?.area, vm.managerResponse?.area_type)
            setValueUpdateProductType(createManagerActivity.spProductType, response?.product_type, vm.managerResponse?.product_type,
                vm.managerResponse?.land_type)
            setValueUpdateCity(createManagerActivity.spCity, response?.city, vm.managerResponse?.city_id, vm.managerResponse?.district_id)

            var words = vm.managerResponse?.images?.split(",")
            words?.let {
                for (w in words) {
                    list.add(LinkResponse(url = w))
                }
            }
            initUploadImageAdapter()


        }
    }


    private fun getOption() {
        vm.getOption(onSuccess = {
            spinnerProductType(createManagerActivity.spProductType, it?.product_type)
            spinnerCity(createManagerActivity.spCity, it?.city)
            spinnerChildren(createManagerActivity.spinner5, it?.area)
            spinnerChildren(createManagerActivity.spinner6, it?.price_type)

            initView(it)
        }, onFailed = {
            ShowAlert.fail(pContext = this@CreateManagerActivity, message = it)
        })
    }

    private fun spinnerChildren(spinner: Spinner, list: ArrayList<BaseOption>?) {
        var item = ArrayList<String>()
        list?.forEach {
            it?.name?.let { it1 -> item.add(it1) }
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, item)
//        val adapter = ArrayAdapter<String>(this, R.layout.spinner_item, item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (list?.get(position)?.type) {
                    2 -> {
                        vm.land_type = list?.get(position)?.id?.toInt()
                    }
                    3 -> {
                        vm.area_type = list?.get(position)?.id?.toInt()

                    }
                    4 -> {
                        vm.price_type = list?.get(position)?.id?.toInt()

                    }
                    else -> {
                        vm.district_id = list?.get(position)?.id?.toInt()

                    }

                }
            }

        }


    }

    private fun setValueUpdateProductType(spinner: Spinner?, list: ArrayList<ProductResponse>?,
                                          id: String?, landTypeId: String?){
        list?.let {
            for (i in 0 until it.size){
                if (list[i].id == id) {
                    spinner?.setSelection(i)
                    setValueUpdateSpinner(createManagerActivity.spLandType, it[i].children, landTypeId)
                }
            }
        }
    }

    private fun setValueUpdateCity(spinner: Spinner?, list: ArrayList<CityResponse>?,
                                          id: String?, landTypeId: String?){
        list?.let {
            for (i in 0 until it.size){
                if (list[i].id == id) {
                    spinner?.setSelection(i)
                    setValueUpdateSpinner(createManagerActivity.spLandType, it[i].district, landTypeId)
                }
            }
        }
    }

    private fun setValueUpdateSpinner(spinner: Spinner?, list: ArrayList<BaseOption>?, id: String?) {
        list?.let {
            for (i in 0 until it.size){
                if (list[i].id == id) {
                    spinner?.setSelection(i)
                }
            }
        }

    }


    private fun spinnerProductType(spinner: Spinner, list: ArrayList<ProductResponse>?) {

        var item = ArrayList<String>()

        list?.forEach {
            it?.name?.let { it1 -> item.add(it1) }
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, item)

        spinner.adapter = adapter

        var children = vm.product_type?.let { list?.get(it)?.children }
        spinnerChildren(createManagerActivity.spLandType, children)

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                vm.product_type = list?.get(position)?.id?.toInt()
                children = spinner.selectedItemPosition?.let { list?.get(it)?.children }
                spinnerChildren(createManagerActivity.spLandType, children)


            }

        }
    }


    private fun spinnerCity(spinner: Spinner, list: ArrayList<CityResponse>?) {

        var item = ArrayList<String>()

        list?.forEach {
            it?.name?.let { it1 -> item.add(it1) }
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, item)

        spinner.adapter = adapter

        var children = vm.city_id?.let { list?.get(it)?.district }
        spinnerChildren(createManagerActivity.spDistrict, children)

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                vm.city_id = list?.get(position)?.id?.toInt()
                children = spinner.selectedItemPosition?.let { list?.get(it)?.district }
                spinnerChildren(createManagerActivity.spDistrict, children)
            }

        }
    }


    private fun addManager() {
        if (Validator().validate(createManagerActivity)) {

            ShowLoading.show(this@CreateManagerActivity)
            vm.fullname = createManagerActivity.telFullName.text.toString()
            vm.phone = createManagerActivity.telPhone.text.toString()
            vm.email = createManagerActivity.telEmail.text.toString()
            vm.title = createManagerActivity.telTilte.text.toString()
            vm.address = createManagerActivity.telAddress.text.toString()
            vm.area = createManagerActivity.telArea.text.toString()
            vm.price = createManagerActivity.telPrice.text.toString()
            vm.description = createManagerActivity.editDescription.text.toString()
            vm.images = list.joinToString { it -> "\'${it.url}\'".replace("'", "") }
            Log.e("Tag: ", vm.images)
            vm.addManager(onSuccess = {
                ShowLoading.dismiss()
                ShowAlert.fail(pContext = this, dialogTitle = getString(R.string.alert_title_inform), message = it)
                setResult(IntentActionKeys.RELOAD_DATA)
                finish()
            }, onFailed = {
                ShowLoading.dismiss()
                ShowAlert.fail(pContext = this, message = it)
            })
        }
    }



    private fun update() {
        if (Validator().validate(createManagerActivity)) {
            ShowLoading.show(this@CreateManagerActivity)
            vm.fullname = createManagerActivity.telFullName.text.toString()
            vm.phone = createManagerActivity.telPhone.text.toString()
            vm.email = createManagerActivity.telEmail.text.toString()
            vm.title = createManagerActivity.telTilte.text.toString()
            vm.address = createManagerActivity.telAddress.text.toString()
            vm.area = createManagerActivity.telArea.text.toString()
            vm.price = createManagerActivity.telPrice.text.toString()
            vm.description = createManagerActivity.editDescription.text.toString()

            vm.images = list.joinToString { it -> "\'${it.url}\'".replace("'", "") }
            vm.updateManager(onSuccess = {
                ShowLoading.dismiss()
                ShowAlert.fail(pContext = this, dialogTitle = getString(R.string.alert_title_inform), message = it)
                setResult(IntentActionKeys.RELOAD_DATA)
                finish()
            }, onFailed = {
                ShowLoading.dismiss()
                ShowAlert.fail(pContext = this, message = it)
            })
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

    private fun handleCrop(resultCode: Int, result: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            uploadImage(Crop.getOutput(result).path)
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this@CreateManagerActivity, getString(R.string.text_error), Toast.LENGTH_LONG).show()
        }
    }

    private fun uploadImage(pathTofFile: String?){
        ShowLoading.show(this@CreateManagerActivity)
        vm.uploadImage(pathTofFile, onSuccess = {
            ShowLoading.dismiss()
            list.add(LinkResponse(url = it))
            initUploadImageAdapter()
        }, onFailed = {
            ShowAlert.fail(pContext = this@CreateManagerActivity, message = it)
            ShowLoading.dismiss()
        })
    }

    private fun initUploadImageAdapter(){
        val recyclerView = createManagerActivity.rvImageUpload
        var adapter = CreateManagerAdapter(list)
        recyclerView.layoutManager = LinearLayoutManager(this@CreateManagerActivity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
    }

}
