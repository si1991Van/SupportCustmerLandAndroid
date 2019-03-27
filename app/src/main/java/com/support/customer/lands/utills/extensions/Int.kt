package com.support.customer.lands.utills.extensions

fun Int.statusConsignment(): String?{
    when(this){
        1 ->{
            return "Cho xac nhan"
        }

        2 ->{
            return "Da nhan"
        }
        3 ->{
            return "Hoan thanh"
        }
        4 ->{
            return "Da huy"
        }

    }
    return null
}