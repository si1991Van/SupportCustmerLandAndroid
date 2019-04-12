package com.support.customer.lands.viewcontroller.home.tab

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.support.customer.lands.R
import com.support.customer.lands.databinding.FragmentTabPhoneBinding
import com.support.customer.lands.model.ProjectResponse
import com.support.customer.lands.utills.CommonUtil
import com.support.customer.lands.utills.dialog.ShowAlert
import com.support.customer.lands.utills.dialog.ShowLoading
import com.support.customer.lands.utills.extensions.fromUrl
import com.support.customer.lands.viewcontroller.home.FirebaseChatActivity
import com.support.customer.lands.viewcontroller.home.MainActivity
import com.support.customer.lands.viewmodel.TabPhoneViewModel
import haiphat.com.bds.nghetuvan.view.BaseFragment



class TabPhoneFragment : BaseFragment() {

    private lateinit var tabPhoneFragment: FragmentTabPhoneBinding
    private var vm = TabPhoneViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tabPhoneFragment = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_phone, container, false)
        (activity as MainActivity).setHeaderTitle(vm.projectResponse?.name)
        tabPhoneFragment.txtNameProject.text = vm.projectResponse?.name
        tabPhoneFragment.btnHotline.text = vm.projectResponse?.hotline?.let {
            getString(R.string.txt_hotline, it)
        } ?: getString(R.string.txt_hotline, "Chưa cập nhật")
        tabPhoneFragment.btnEmail.text = vm.projectResponse?.email?.let {
            getString(R.string.txt_email, vm.projectResponse?.email)
        } ?: getString(R.string.txt_email, "Chưa cập nhật")
        vm.projectResponse?.rank_project?.let {
            tabPhoneFragment.rating.rating = it.toFloat()
        }

        tabPhoneFragment.imgLogo.fromUrl(vm.projectResponse?.image_url, placeHolder = R.drawable.ic_logo)
        tabPhoneFragment.rating.setIsIndicator(true)
        if (vm.projectResponse?.rank_project == 0) {
            tabPhoneFragment.rating.setIsIndicator(false)
            tabPhoneFragment.rating.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                    postRatting(rating.toInt())
            }
        }

        tabPhoneFragment.btnHotline.setOnClickListener {
            vm?.projectResponse?.hotline?.let {
                CommonUtil.clickAndCall(activity, it)
            }

        }
        tabPhoneFragment.btnEmail.setOnClickListener {
            vm?.projectResponse?.email?.let {
                CommonUtil.clickSendMail(activity, it)
            }
        }

        tabPhoneFragment.btnLiveChat.setOnClickListener {
            startActivity(Intent(activity, FirebaseChatActivity::class.java))
        }

        return tabPhoneFragment.root
    }

    private fun postRatting(rank_project: Int) {
        ShowLoading.show(activity)
        vm.postRatting(rank_project, onSuccess = {
            tabPhoneFragment.rating.setIsIndicator(true)
            ShowLoading.dismiss()
        }, onFailed = {
            ShowLoading.dismiss()
            ShowAlert.fail(pContext = activity, message = it)
        })

    }


    companion object {
        fun newInstance(response: ProjectResponse?, arguments: Bundle? = null): TabPhoneFragment {
            val fragment = TabPhoneFragment()
            fragment.arguments = arguments
            fragment.vm.projectResponse = response
            return fragment
        }


    }

}