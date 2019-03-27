package com.support.customer.lands.viewmodel

import com.support.customer.lands.model.*
import com.support.customer.lands.services.GsonUtil
import com.support.customer.lands.services.UserServices
import com.support.customer.lands.services.api.ManagerApi
import com.support.customer.lands.services.api.OauthApi
import com.support.customer.lands.services.api.UploadImageApi

class CreateManagerViewModel {
    var fullname: String? = null
    var phone: String? = null
    var email: String? = null
    var title: String? = null
    var product_type: Int? = 0
    var land_type: Int? = 0
    var address: String? = null
    var city_id: Int? = 0
    var district_id: Int? = 0
    var area: String?= null
    var area_type: Int?= 0
    var price: String?= null
    var price_type: Int?= 0
    var description: String?= null
    var images: String?= null
    var type: Int? = 0
    var managerResponse : ManagerResponse? = null

    fun getOption(onSuccess : (OptionResponse?) ->Unit, onFailed : (String?) -> Unit){
        ManagerApi().getOption {
            var response = GsonUtil.fromJson(it.responseContent, GetOptionManagerResponse::class.java)
            if (it.isSuccess()){
                onSuccess(response?.data)
            }else{
                onFailed(it.getErrorMessage())
            }

        }
    }


    fun addManager(onSuccess : (String?) ->Unit, onFailed : (String?) -> Unit){
        ManagerApi().addConsignment(fullname, phone, email, title, product_type.toString(), land_type.toString(), address, city_id.toString()
        , district_id.toString(), area, area_type.toString(), price, price_type.toString(), description, images, onResponse = {

            var response = GsonUtil.fromJson(it.responseContent, BaseResponse::class.java)
            if (it.isSuccess()){
                onSuccess(response?.message)
            }else{
                onFailed(it.getErrorMessage())
            }

        })
    }

    fun updateManager(onSuccess : (String?) ->Unit, onFailed : (String?) -> Unit){
        ManagerApi().updateConsignment(managerResponse?.id, fullname, phone, email, title, product_type.toString(), land_type.toString(), address, city_id.toString()
            , district_id.toString(), area, area_type.toString(), price, price_type.toString(), description, images, onResponse = {
                var response = GsonUtil.fromJson(it.responseContent, BaseResponse::class.java)
                if (it.isSuccess()){
                    onSuccess(response?.message)
                }else{
                    onFailed(it.getErrorMessage())
                }

            })
    }

    fun uploadImage(path: String?, onSuccess : (String?) ->Unit, onFailed : (String?) -> Unit){
        UploadImageApi().uploadImage(path) {
            val response = GsonUtil.fromJson(it.responseContent, ImageResponse::class.java)
            if(response?.status!!){
                onSuccess(response?.data?.url)
            }else{
                onFailed(it.getErrorMessage())
            }
        }

    }
}
