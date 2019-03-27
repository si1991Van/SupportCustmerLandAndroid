package com.support.customer.lands.model

class GetOptionManagerResponse : BaseResponse<OptionResponse>()


class OptionResponse{

    var product_type: ArrayList<ProductResponse>? = null
    var city: ArrayList<CityResponse>? = null
    var area: ArrayList<BaseOption>? = null
    var price_type: ArrayList<BaseOption>? = null

}

class ProductResponse: BaseOption(){
    var children: ArrayList<BaseOption>? = null
}

class CityResponse: BaseOption() {
    var district: ArrayList<BaseOption>? = null
}

open class BaseOption{
    var id : String? = null
    var name: String? = null
    var type: Int? = 0
}