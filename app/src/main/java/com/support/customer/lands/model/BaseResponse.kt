package com.support.customer.lands.model

open class BaseResponse<T> {
    var status: Int? = 0
    var data: T? = null
    var message: String? = null
}