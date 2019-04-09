package com.support.customer.lands.services.api

import android.text.TextUtils
import android.util.Log
import com.support.customer.lands.services.DgmResponse
import com.support.customer.lands.services.GsonUtil
import com.support.customer.lands.services.OkHttpService
import okhttp3.*
import java.net.URLEncoder
import java.util.ArrayList

abstract class BaseApi {

    protected abstract fun apiUrl(): String?
    protected abstract fun getEndPoint() : String?

    protected operator fun get(queryString: String, completed: (DgmResponse)->Unit) {
        this.request(this.getEndPoint() + "/" + this.apiUrl() + queryString,
            OkHttpService.Method.GET, null, null, completed)
    }

    protected fun put(method: String, body: Map<String, String>, completed: (DgmResponse)->Unit) {
        val headerBuilder = Headers.Builder()
        headerBuilder.add("Content-Type", "application/json")
        this.request(this.getEndPoint() + "/" + this.apiUrl() + method,
            OkHttpService.Method.PUT, makeBody(body), headerBuilder, completed)
    }

    protected fun putString(method: String, body: String, completed: (DgmResponse)->Unit) {
        val headerBuilder = Headers.Builder()
        headerBuilder.add("Content-Type", "application/json")
        this.request(this.getEndPoint() + "/" + this.apiUrl() + method,
            OkHttpService.Method.PUT, RequestBody.create(MediaType.parse("application/json"), body), headerBuilder, completed)
    }

    protected fun delete(method: String, body: Map<String, String>, completed: (DgmResponse)->Unit) {
        val headerBuilder = Headers.Builder()
        headerBuilder.add("Content-Type", "application/json")
        this.request(this.getEndPoint() + "/" + this.apiUrl() + method,
            OkHttpService.Method.DELETE, makeBody(body), headerBuilder, completed)
    }

    fun post(method: String, body: Map<String, String>, completed: (DgmResponse)->Unit) {
        val headerBuilder = Headers.Builder()
        headerBuilder.add("Content-Type", "application/json")
        Log.e("tag: ", method)
        this.request(this.getEndPoint() + "/" + this.apiUrl() + method,
            OkHttpService.Method.POST, makeBody(body), headerBuilder, completed)
    }

    protected fun postString(method: String, body: String, completed: (DgmResponse)->Unit) {
        val headerBuilder = Headers.Builder()
        headerBuilder.add("Content-Type", "application/json")
        this.request(this.getEndPoint() + "/" + this.apiUrl() + method,
            OkHttpService.Method.POST, RequestBody.create(MediaType.parse("application/json"), body), headerBuilder, completed)
    }

    protected fun postUrlEncoded(method: String, body: Map<String, String>, completed: (DgmResponse)->Unit) {
        val headerBuilder = Headers.Builder()
        headerBuilder.add("Content-Type", "application/x-www-form-urlencoded")
        this.request(this.getEndPoint() + "/" + this.apiUrl() + method,
            OkHttpService.Method.POST, makeUrlEncodeBody(body), headerBuilder, completed)
    }

    protected fun postUrlEncoded(method: String, body: Map<String, String>): DgmResponse? {
        val headerBuilder = Headers.Builder()
        headerBuilder.add("Content-Type", "application/x-www-form-urlencoded")
        return this.request(this.getEndPoint() + "/" + this.apiUrl() + method,
            OkHttpService.Method.POST, makeUrlEncodeBody(body), headerBuilder)
    }

    fun upload(method: String, builder: MultipartBody.Builder, completed: (DgmResponse)->Unit) {
        this.request(this.getEndPoint() + "/" + this.apiUrl() + method,
            OkHttpService.Method.POST, builder.build(), null, completed)
    }

    fun upload(method: String, body: Map<String, String>, completed: (DgmResponse)->Unit) {
        this.request(this.getEndPoint() + "/" + this.apiUrl() + method,
            OkHttpService.Method.POST, makeUrlEncodeBody(body), null, completed)
    }

    private fun request(url: String, method: OkHttpService.Method, pRequestBody: RequestBody?,
                        headerBuilder: Headers.Builder?, completed: (DgmResponse)->Unit) {
        OkHttpService.request(url, method, pRequestBody, headerBuilder, completed)

    }

    private fun request(url: String, method: OkHttpService.Method, pRequestBody: RequestBody, headerBuilder: Headers.Builder): DgmResponse? {
        return OkHttpService.request(url, method, pRequestBody, headerBuilder)
    }

    private fun makeBody(body: Map<String, String>): RequestBody {
        return RequestBody.create(MediaType.parse("application/json"), GsonUtil.toJson(body))
    }

    private fun makeUrlEncodeBody(body: Map<String, String>): RequestBody {
        val formBuilder = FormBody.Builder()
        for ((key, value) in body) {
            formBuilder.add(key, value)
        }
        return formBuilder.build()
    }

    protected fun parseUrlWithParams(url: String, params: Map<String, String>?): String {
        val fragments = ArrayList<String>()
        if (params != null && !params.isEmpty()) {
            val urlParams = url.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (item in urlParams) {
                if (item.startsWith("{") && item.endsWith("}")) {
                    val paramName = item.replace("{", "").replace("}", "")
                    if (params.containsKey(paramName)) {
                        var value: String? = params[paramName]
                        value = if (value == null) "" else value
                        var appendValue: String = value
                        try {
                            appendValue = URLEncoder.encode(value, "utf-8")
                        } catch (ex: Exception) {
                        }

                        fragments.add(appendValue)
                    }
                } else {
                    fragments.add(item)
                }
            }
        }
        return TextUtils.join("/", fragments)
    }

    protected fun parseUrlQueryStringWithParams(url: String, params: Map<String, String>?): String {
        var ret = url
        if (params != null && !params.isEmpty()) {
            val urlParams = url.split("[&?/]".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (item in urlParams) {
                if (item.startsWith("{") && item.endsWith("}")) {
                    val paramName = item.replace("{", "").replace("}", "")
                    val value = params[paramName]
                    if (value != null) {
                        var appendValue: String = value
                        try {
                            appendValue = URLEncoder.encode(value, "utf-8")
                        } catch (ex: Exception) {

                        }

                        ret = ret.replace(item, paramName + "=" + appendValue)
                    } else {
                        ret = ret.replace(item, "")
                    }
                }
            }
        }
        return ret
    }
}