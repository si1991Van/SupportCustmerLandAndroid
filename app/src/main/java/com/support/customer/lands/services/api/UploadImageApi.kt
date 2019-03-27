package com.support.customer.lands.services.api

import com.support.customer.lands.services.Config
import com.support.customer.lands.services.DgmResponse
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.HashMap

class UploadImageApi : BaseApi(){

    override fun apiUrl(): String? {
        return ""
    }

    override fun getEndPoint(): String? {
        return Config.URL_UPLOAD_IMAGE
    }

    fun uploadImage(path: String?,  onResponse: (DgmResponse) -> Unit) {
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        val fileUpload = File(path)
        val fileRequestBody = RequestBody.create(MediaType.parse("image/*"), fileUpload)
        builder.addFormDataPart("file", fileUpload.name, fileRequestBody)
        this.upload("file", builder, onResponse)
    }

}