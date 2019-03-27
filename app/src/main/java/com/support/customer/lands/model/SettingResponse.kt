package com.support.customer.lands.model



class ListSettingResponse: BaseListResponse<SettingResponse>()


class SettingResponse (var id: String? = null,
                       var name: String? = null,
                       var status: Boolean? = false)