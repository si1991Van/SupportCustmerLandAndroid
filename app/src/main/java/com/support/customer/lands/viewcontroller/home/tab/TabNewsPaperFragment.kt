package com.support.customer.lands.viewcontroller.home.tab

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.support.customer.lands.R
import com.support.customer.lands.databinding.FragmentTabNewsPaperBinding
import com.support.customer.lands.model.CategoryNewsPaperResponse
import com.support.customer.lands.utills.dialog.ShowAlert
import com.support.customer.lands.utills.dialog.ShowLoading
import com.support.customer.lands.viewcontroller.home.MainActivity
import com.support.customer.lands.viewcontroller.home.tab.tab_news_paper.NewsPaperItemFragment
import com.support.customer.lands.viewmodel.TabNewsViewModel
import haiphat.com.bds.nghetuvan.view.BaseFragment
import java.util.ArrayList

class TabNewsPaperFragment : BaseFragment() {

    private lateinit var tabNewsPaperFragment: FragmentTabNewsPaperBinding
    private var vm = TabNewsViewModel()
    var rootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tabNewsPaperFragment = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_news_paper, container, false)
        (activity as MainActivity).setHeaderTitle(getString(R.string.title_project))
//        if (userVisibleHint && !isDataLoaded()) {
            getCategory()
//        }
        rootView = tabNewsPaperFragment.root
        return rootView
    }


    private fun getCategory() {
        ShowLoading.show(activity)
        vm.getCategory(onSuccess = {
            val sectionsPagerAdapter = SectionsPagerNewsAdapter(childFragmentManager)

            it?.let {
                sectionsPagerAdapter.listItemNews = it
            }
            sectionsPagerAdapter.projectId = vm.projectId
            tabNewsPaperFragment.container.adapter = sectionsPagerAdapter
            tabNewsPaperFragment.tabs.setupWithViewPager(tabNewsPaperFragment.container)
            tabNewsPaperFragment.tabs.setTabTextColors(ContextCompat.getColor(context!!, R.color.colorText21), ContextCompat.getColor(context!!, R.color.colorPrimary))
            ShowLoading.dismiss()
        }, onFailed = {
            ShowLoading.dismiss()
            ShowAlert.fail(pContext = activity, message = it)
        })
    }

//    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
//        super.setUserVisibleHint(isVisibleToUser)
//        if (!isVisibleToUser || rootView == null) {
//            return
//        }
//        if (!isDataLoaded()) {
//            getCategory()
//        }
//    }
//
//    private fun isDataLoaded(): Boolean {
//        return vm.list != null && vm.list?.size!! > 0
//    }

    inner class SectionsPagerNewsAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        var listItemNews = ArrayList<CategoryNewsPaperResponse>()
        var projectId: String? = null

        override fun getItem(position: Int): Fragment {
            return NewsPaperItemFragment.newInstance(listItemNews[position].id.toString(), projectId)
        }

        override fun getCount(): Int {
            return listItemNews.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return listItemNews[position].name
        }
    }

    companion object {
        fun newInstance(id: String?, arguments: Bundle? = null): TabNewsPaperFragment {
            val fragment = TabNewsPaperFragment()
            fragment.arguments = arguments
            fragment.vm.projectId = id
            return fragment
        }


    }
}