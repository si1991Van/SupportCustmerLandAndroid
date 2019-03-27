package com.support.customer.lands.utills.extensions

import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.support.customer.lands.services.Config

fun ImageView.fromUrl(url: String?, placeHolder: Int? = null) {
    var request = Picasso.with(this.context).load(url)
    placeHolder?.let {
        request = request.placeholder(it)
    }
    request.fit()
        .centerCrop()
        .into(this)
}

fun ImageView.fromUrlFixData(url: String?, placeHolder: Int? = null) {
    var request = Picasso.with(this.context).load(url)
    placeHolder?.let {
        request = request.placeholder(it)
    }
    request.fit()
        .centerCrop()
        .into(this)
}