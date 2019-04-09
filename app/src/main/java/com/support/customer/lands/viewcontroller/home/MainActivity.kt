package com.support.customer.lands.viewcontroller.home

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.design.widget.TabLayout
import android.view.View
import com.support.customer.lands.R
import com.support.customer.lands.databinding.ActivityMainBinding
import haiphat.com.bds.nghetuvan.view.BaseActivity
import android.support.v4.app.Fragment
import android.text.TextUtils
import com.support.customer.lands.model.ProjectResponse
import com.support.customer.lands.services.GsonUtil
import com.support.customer.lands.services.UserServices
import com.support.customer.lands.utills.IntentActionKeys
import com.support.customer.lands.utills.IntentDataKeys
import com.support.customer.lands.viewcontroller.auth.LoginActivity
import com.support.customer.lands.viewcontroller.home.tab.*
import com.support.customer.lands.viewcontroller.home.tab.tab_news_paper.NewsDetailActivity

class MainActivity : BaseActivity(){

    private lateinit var mainBinding: ActivityMainBinding

    var projectResponse: ProjectResponse?= null

    override fun getContentView(): View {
        mainBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_main, null, false)
        val bundle = intent.extras
        projectResponse = GsonUtil.fromJson(bundle.getString(IntentActionKeys.KEY_PROJECT), ProjectResponse::class.java)

        mainBinding.navigation.setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when(item.itemId){
                R.id.actionPhone -> {
                    selectedFragment = TabPhoneFragment.newInstance(projectResponse)

                }
                R.id.actionHistory -> {
                    selectedFragment = TabHistoryFragment.newInstance(projectResponse?.id)
                }
                R.id.actionNewsPager -> {
                    selectedFragment = TabNewsPaperFragment.newInstance(projectResponse?.id)
                }
                R.id.actionUser -> {
                    selectedFragment = TabUserFragment.newInstance(projectResponse?.id)
                }
                R.id.actionUtilities -> {
                    selectedFragment = TabManagerFragment()
                }
            }

            selectedFragment?.let {
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout, selectedFragment).addToBackStack(null)
                    .commitAllowingStateLoss()

            }
            true
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, TabPhoneFragment.newInstance(projectResponse)).addToBackStack(null)
        transaction.commit()

        return mainBinding.root
    }

    override fun onBackPressed() {

        setResult(IntentActionKeys.RELOAD_DATA)
        finish()
        super.onBackPressed()
    }





}
