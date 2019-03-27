package com.support.customer.lands.viewcontroller.home.tab.tabUser

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.support.customer.lands.R
import com.support.customer.lands.databinding.ItemTabUserBinding
import com.support.customer.lands.model.ItemSaleProjectResponse
import com.support.customer.lands.services.GsonUtil
import com.support.customer.lands.utills.extensions.fromUrl
import haiphat.com.bds.nghetuvan.view.BaseFragment

class SaleFragment : BaseFragment() {

    private lateinit var dataBindingFragmentNewsInfo: ItemTabUserBinding
    var item: ItemSaleProjectResponse? = null
    var mCallback: OnSelectedListener? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBindingFragmentNewsInfo = DataBindingUtil.inflate(inflater, R.layout.item_tab_user, container, false)
        if (item?.avatar == "/no-avatar.ico"){
            item?.avatar = null
        }
        dataBindingFragmentNewsInfo.imgAvatar.fromUrl(item?.avatar, placeHolder = R.drawable.ic_defaut_avatar)
        dataBindingFragmentNewsInfo.txtName.text = item?.fullname
        dataBindingFragmentNewsInfo.txtProject.text = getString(R.string.txt_tab_user_project_name, item?.name + " - " + item?.project_name)


        dataBindingFragmentNewsInfo.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            item?.rank = rating.toInt()
            dataBindingFragmentNewsInfo.ratingBar.rating = item?.rank?.toFloat()!!

            mCallback?.onSelected(item)
        }
        dataBindingFragmentNewsInfo.ratingBar.rating = item?.rank?.toFloat()!!
        mCallback?.onSelected(item)

        return dataBindingFragmentNewsInfo.root
    }

    interface OnSelectedListener {
        fun onSelected(item: ItemSaleProjectResponse?)
    }



    companion object {
        fun newInstance(item: ItemSaleProjectResponse, arguments: Bundle? = null): SaleFragment {
            val fragment = SaleFragment()
            fragment.arguments = arguments
            fragment.item = item
            return fragment
        }
    }
    
    



}