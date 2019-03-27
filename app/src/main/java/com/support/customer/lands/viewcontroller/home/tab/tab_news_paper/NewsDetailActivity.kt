package com.support.customer.lands.viewcontroller.home.tab.tab_news_paper

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.support.customer.lands.R
import com.support.customer.lands.databinding.ActivityNewsDetailBinding
import com.support.customer.lands.model.NewsPaperItemResponse
import com.support.customer.lands.services.Config
import com.support.customer.lands.services.GsonUtil
import com.support.customer.lands.utills.IntentActionKeys
import com.support.customer.lands.utills.IntentDataKeys
import haiphat.com.bds.nghetuvan.view.BaseActivity

class NewsDetailActivity : BaseActivity(){

    private lateinit var newsDetailBinding: ActivityNewsDetailBinding
    var slug: String? = null
    override fun getContentView(): View {
        newsDetailBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_news_detail, null, false)


        return newsDetailBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var bundle = intent.extras
        var str = bundle.getString(IntentDataKeys.KEY_NOTIFICATION_SLUG)
        val response = GsonUtil.fromJson(bundle.getString(IntentActionKeys.KEY_NEWS_DETAIL), NewsPaperItemResponse::class.java)
        setHeaderTitle(response?.title)

        str?.let {
            slug = it
        }

        response?.slug?.let {
            slug = it
        }

        newsDetailBinding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        newsDetailBinding.webView.settings.javaScriptCanOpenWindowsAutomatically = true
        newsDetailBinding.webView.settings.javaScriptEnabled = true
        newsDetailBinding.webView.settings.pluginState = WebSettings.PluginState.ON
        newsDetailBinding.webView.settings.defaultTextEncodingName = "utf-8"
        newsDetailBinding.webView.loadUrl(Config.URL_DETAIL_NEW + slug)
    }
}
