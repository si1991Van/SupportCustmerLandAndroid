package com.support.customer.lands.viewcontroller.home

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.support.customer.lands.R
import com.support.customer.lands.adapter.ProjectAdapter
import com.support.customer.lands.databinding.ActivityProjectBinding
import com.support.customer.lands.model.ProjectResponse
import com.support.customer.lands.services.GsonUtil
import com.support.customer.lands.services.UserServices
import com.support.customer.lands.utills.IntentActionKeys
import com.support.customer.lands.utills.IntentDataKeys
import com.support.customer.lands.utills.dialog.ShowAlert
import com.support.customer.lands.utills.dialog.ShowLoading
import com.support.customer.lands.viewcontroller.auth.LoginActivity
import com.support.customer.lands.viewcontroller.home.tab.tab_news_paper.NewsDetailActivity
import com.support.customer.lands.viewmodel.HomeViewModel
import haiphat.com.bds.nghetuvan.view.BaseActivity

class ProjectActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {


    private lateinit var homeBinding: ActivityProjectBinding
    private var vm = HomeViewModel()
    private var lastBackPressTime: Long = 0
    private var toastBackToExit: Toast? = null

    override fun getContentView(): View {
        homeBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_project, null, false)
        homeBinding.swipeRefreshLayout.setOnRefreshListener(this)
        homeBinding.swipeRefreshLayout.isRefreshing = true
        checkOpenFromNotificationBar()
        return homeBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseActivityBinding.rippleBack.visibility = View.INVISIBLE
        setHeaderTitle(getString(R.string.title_project))
        getItem()
    }

    private fun initAdapter(list: ArrayList<ProjectResponse>?) {
        val recyclerView = homeBinding.rivProject
        val adapter = ProjectAdapter(list, onClick = {
            val intent = Intent(this@ProjectActivity, MainActivity::class.java)
            intent.putExtra(IntentActionKeys.KEY_PROJECT, it?.let { it1 -> GsonUtil.toJson(it1) })
            startActivityForResult(intent, IntentActionKeys.MAIN_ACTIVITY)
        })
        recyclerView.layoutManager = GridLayoutManager(this@ProjectActivity, 3)
        recyclerView.adapter = adapter
    }

    override fun onBackPressed() {
        if (this.lastBackPressTime < System.currentTimeMillis() - 3000) {
            toastBackToExit = Toast.makeText(this, getString(R.string.text_confirm_finish_app), Toast.LENGTH_LONG)
            toastBackToExit?.show()
            this.lastBackPressTime = System.currentTimeMillis()
        } else {
            if (toastBackToExit != null) toastBackToExit?.cancel()
            finish()
        }
    }

    private fun getItem() {
        homeBinding.swipeRefreshLayout.isRefreshing = true
        vm.getItem(onSuccess = {
            initAdapter(it)
            if (it != null && it?.size > 0) {
                homeBinding.txtNoData.visibility = View.GONE
            } else homeBinding.txtNoData.visibility = View.VISIBLE
            homeBinding.swipeRefreshLayout.isRefreshing = false
        }, onFailed = {
            homeBinding.swipeRefreshLayout.isRefreshing = false
            ShowAlert.fail(pContext = this@ProjectActivity, message = it)
        })
    }

    override fun onRefresh() {
        getItem()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IntentActionKeys.MAIN_ACTIVITY) {
            getItem()
        }
    }

    private fun checkOpenFromNotificationBar() {
        val args = intent.extras
        args?.let {
            val slug = args?.getString(IntentDataKeys.KEY_NOTIFICATION_SLUG)
            if (TextUtils.isEmpty(slug)) {
                return
            }
            if (!UserServices.isLoggedIn()) {
                startActivity(Intent(this@ProjectActivity, LoginActivity::class.java))
                return
            }
            var intent = Intent(this@ProjectActivity, NewsDetailActivity::class.java)
            intent.putExtra(IntentDataKeys.KEY_NOTIFICATION_SLUG, slug)
            startActivity(intent)
        }


    }
}
