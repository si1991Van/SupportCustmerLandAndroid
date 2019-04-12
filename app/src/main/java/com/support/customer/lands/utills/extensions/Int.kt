package com.support.customer.lands.utills.extensions

fun Int.statusConsignment(): String?{
    when(this){
        1 ->{
            return "Chờ xác nhận"
        }
        2 ->{
            return "Đã nhận"
        }
        3 ->{
            return "Hoàn thành"
        }
        4 ->{
            return "Đã huỷ"
        }

    }
    return null
}