package com.support.customer.lands.model

class ImageResponse {
    var status: Boolean? = false
    var message: String? = null
    var data: LinkResponse? = null


}

class LinkResponse(
    var url: String? = null
)