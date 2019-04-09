package com.support.customer.lands.services.api

import com.support.customer.lands.services.Config
import com.support.customer.lands.services.DgmResponse
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.HashMap

class OauthApi : BaseApi() {

    override fun apiUrl(): String? {
        return "user/"
    }

    override fun getEndPoint(): String? {
        return Config.API_URL
    }

    fun login(username: String?, password: String?, onResponse: (DgmResponse) -> Unit) {
        val data = HashMap<String, String>()
        data.put("phone", username ?: "")
        data.put("password", password ?: "")
        this.postUrlEncoded("login", data, onResponse)
    }

    fun logout(devicesId: String?, devicesToken: String?, onResponse: (DgmResponse) -> Unit) {
        val data = HashMap<String, String>()
        data.put("device_id", devicesId ?: "")
        data.put("token", devicesToken ?: "")
        this.upload("logout", data, onResponse)
    }

    fun forgotPassword(phone: String?,  onResponse: (DgmResponse) -> Unit) {
        val data = HashMap<String, String>()
        data.put("phone", phone ?: "")
        this.upload("forgot", data, onResponse)
    }

    fun changePassword(currentPassword: String?, newPassword: String?, newPasswordConfirmation: String?,  onResponse: (DgmResponse) -> Unit) {
        val data = HashMap<String, String>()
        data.put("current_password", currentPassword ?: "")
        data.put("new_password", newPassword ?: "")
        data.put("new_password_confirmation", newPasswordConfirmation ?: "")
        this.upload("change-password", data, onResponse)
    }

    fun updateProfile(fullName: String?, email: String?, birthday: String?,   onResponse: (DgmResponse) -> Unit) {
        val data = HashMap<String, String>()
        data.put("fullname", fullName ?: "")
        data.put("email", email ?: "")
        data.put("birthday", birthday ?: "")
        this.upload("update-profile", data, onResponse)
    }

    fun updateAvatar(path: String?,  onResponse: (DgmResponse) -> Unit) {
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        val fileUpload = File(path)
        val fileRequestBody = RequestBody.create(MediaType.parse("image/*"), fileUpload)
        builder.addFormDataPart("file", fileUpload.name, fileRequestBody)
        this.upload("update-avatar", builder, onResponse)

    }

    fun getSetting(onResponse: (DgmResponse) -> Unit){
        this["get-follower", onResponse]
    }

    fun postSetting(id: String?, status: String?, onResponse: (DgmResponse) -> Unit){
        val data = HashMap<String, String>()
        data.put("id", id ?: "")
        data.put("status", status ?: "")
        this.upload("post-follower", data, onResponse)
    }

    fun updateNotificationToken(typeDevice: String?, deviceName: String?, deviceId: String?, token: String?, onResponse: (DgmResponse) -> Unit) {
        val data = HashMap<String, String>()
        data.put("type_device", typeDevice?: "")
        data.put("device_name", deviceName?: "")
        data.put("device_id", deviceId?: "")
        data.put("token", token ?: "")
        this.post("update-token-fire-base", data, onResponse)
    }

    fun postBooked(fullName: String?, phone: String?, email: String?, timeView: String?, note: String?, onResponse: (DgmResponse) -> Unit) {
        val data = HashMap<String, String>()
        data.put("fullname", fullName?: "")
        data.put("phone", phone?: "")
        data.put("email", email?: "")
        data.put("time_view", timeView?: "")
        data.put("note", note?: "")
        this.post("booking-view-house", data, onResponse)
    }

}