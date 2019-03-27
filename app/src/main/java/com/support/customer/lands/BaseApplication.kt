package com.support.customer.lands

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.support.customer.lands.utills.SharePreferencesLoaders

class BaseApplication : MultiDexApplication() {

    companion object{
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        MultiDex.install(this)
        SharePreferencesLoaders.init(this)
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks{
            override fun onActivityPaused(p0: Activity?) {
            }

            override fun onActivityResumed(p0: Activity?) {
            }

            override fun onActivityStarted(p0: Activity?) {
            }

            override fun onActivityDestroyed(p0: Activity?) {
            }

            override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
            }

            override fun onActivityStopped(p0: Activity?) {
            }

            override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
                p0?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }

        })
    }
}