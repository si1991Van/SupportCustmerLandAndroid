package com.support.customer.lands.viewcontroller.home.tab

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.support.customer.lands.R
import com.support.customer.lands.databinding.FragmentTabUserBinding
import com.support.customer.lands.model.ItemSaleProjectResponse
import com.support.customer.lands.services.GsonUtil
import com.support.customer.lands.utills.dialog.ShowAlert
import com.support.customer.lands.viewcontroller.home.MainActivity
import com.support.customer.lands.viewcontroller.home.tab.tabUser.SaleFragment
import com.support.customer.lands.viewmodel.TabUserViewModel
import haiphat.com.bds.nghetuvan.view.BaseFragment

class TabUserFragment : BaseFragment() {

    private lateinit var tabUserFragment: FragmentTabUserBinding
    var vm = TabUserViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tabUserFragment = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_user, container, false)

        (activity as MainActivity).setHeaderTitle(getString(R.string.txt_tab_user_title))
        getSale()
        tabUserFragment.button.setOnClickListener {
            postFeedback()
        }
        return tabUserFragment.root
    }

    private fun initSaleAdapter(list: ArrayList<ItemSaleProjectResponse>?){
        val sectionsPagerAdapter = SectionsPagerNewsAdapter(childFragmentManager)
        list?.let {
            sectionsPagerAdapter?.listItemNews = list
        }
        tabUserFragment.container.adapter = sectionsPagerAdapter
    }


    private fun postFeedback(){
        vm.note = tabUserFragment.editText.text.toString()

        vm.postFeedback(onSuccess = {
            tabUserFragment.editText.text.clear()
            ShowAlert.fail(pContext = activity, message = it, dialogTitle = getString(R.string.alert_title_inform))
            getSale()
        }, onFailed = {
            ShowAlert.fail(pContext = activity, message = it)
        })
    }


    private fun getSale(){
        vm.getSale(onSuccess = {
            initSaleAdapter(it)
        }, onFailed = {
            ShowAlert.fail(pContext = activity, message = it)
        })

    }

    inner class SectionsPagerNewsAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        var listItemNews = ArrayList<ItemSaleProjectResponse>()

        override fun getItem(position: Int): Fragment {
            val fragment = SaleFragment.newInstance(listItemNews[position])
            fragment.mCallback = object : SaleFragment.OnSelectedListener{
                override fun onSelected(item: ItemSaleProjectResponse?) {
                    vm.rank = item?.rank
                    vm.saleId = item?.id
                    vm.transactionId = item?.transaction_id
                }

            }
            return fragment
        }

        override fun getCount(): Int {
            return listItemNews.size
        }
    }



    companion object {
        fun newInstance(id: String?, arguments: Bundle? = null): TabUserFragment {
            val fragment = TabUserFragment()
            fragment.arguments = arguments
            fragment.vm.id = id
            return fragment
        }


    }
}