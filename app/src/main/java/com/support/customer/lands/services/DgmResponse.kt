package com.support.customer.lands.services

import android.text.TextUtils
import com.support.customer.lands.BaseApplication
import com.support.customer.lands.R
import com.support.customer.lands.model.BaseResponse
import com.support.customer.lands.model.ErrorModel
import okhttp3.Response
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException

open class DgmResponse {
    var status: Int? = 0
    var responseContent: String? = null
    private var exception: Exception? = null
    var message : String? = null

    constructor(exception: Exception) {
        this.exception = exception
    }

    constructor(response: Response?) {
        try {
            this.responseContent = response?.body()?.string().toString()
            val response = GsonUtil.fromJson(responseContent, BaseResponse::class.java)
            this.message = response?.message
            this.status = response?.status

        } catch (ex: Exception) {
            this.exception = ex
        }
    }

    fun isSuccess(): Boolean {
        return status == 200
    }

    fun getErrorMessage(): String? {
        if (status == 401) {
            return message?.let { it } ?: BaseApplication.context.getString(R.string.text_error)
        }
        if (TextUtils.isEmpty(responseContent) && status == 0) {
            if (exception is TimeoutException || exception is SocketTimeoutException) {
                return BaseApplication.context.getString(R.string.text_cannot_connect_to_server)
            } else {
                return BaseApplication.context.getString(R.string.text_no_internet_connection)
            }
        }
        return message?.let { it } ?: BaseApplication.context.getString(R.string.text_error)

    }

    fun hasNetwordError(): Boolean {
        return this.exception != null
    }
}