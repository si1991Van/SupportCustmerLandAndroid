package com.support.customer.lands.viewmodel

import com.support.customer.lands.model.HistoryTitleResponse
import com.support.customer.lands.model.ListHistoryResponse
import com.support.customer.lands.services.GsonUtil
import com.support.customer.lands.services.api.ProjectApi

class TabHistoryViewModel {

    var id: String? = null
    var list : ArrayList<HistoryTitleResponse>? = null

    fun getItem(onSuccess : (ArrayList<HistoryTitleResponse>)-> Unit, onFailed : (String?) -> Unit){
        ProjectApi().postHistoryPayment(id, onResponse = {
            var response = GsonUtil.fromJson(it.responseContent, ListHistoryResponse::class.java)
            if (it.isSuccess()){
                response?.data?.let { onSuccess(it) } ?: onFailed(response?.message)
            }else {
                onFailed(it.getErrorMessage())
            }
        })

    }

//    private fun mockData(): ArrayList<HistoryTitleResponse>{
//        var item = ArrayList<HistoryTitleResponse>()
//        item.add(HistoryTitleResponse(name = "Lịch sử giao dịch căn A0502 - Roman Plaza", item = mockDataItemHistory()))
//        item.add(HistoryTitleResponse(name = "Lịch sử giao dịch căn A0602 - Roman Plaza", item = mockDataItemHistory()))
//        item.add(HistoryTitleResponse(name = "Lịch sử giao dịch căn A0702 - Roman Plaza", item = mockDataItemHistory()))
//        return item
//
//    }
//
//    private fun mockDataItemHistory(): ArrayList<HistoryResponse>{
//        var item = ArrayList<HistoryResponse>()
//        item.add(HistoryResponse(type = "Đặt cọc", time = "20/10/2018", status = "Đã thực hiện"))
//        item.add(HistoryResponse(type = "Ký HĐMB", time = "20/10/2018", status = "Đã thực hiện"))
//        item.add(HistoryResponse(type = "Thanh toán lần 2", time = "20/10/2018", status = "Đã thực hiện"))
//        item.add(HistoryResponse(type = "Thanh toán lần 3", time = "20/10/2018", status = "Đã thực hiện"))
//        item.add(HistoryResponse(type = "Thanh toán lần 4", time = "20/10/2018", status = "Chưa thanh toán"))
//        item.add(HistoryResponse(type = "Thanh toán lần 5", time = "20/10/2018", status = "Chưa thanh toán"))
//        return item
//    }
}