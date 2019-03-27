package com.support.customer.lands.viewcontroller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.support.customer.lands.R
import com.support.customer.lands.fcm.CustomFirebaseMessagingService
import com.support.customer.lands.fcm.NotificationService
import com.support.customer.lands.services.UserServices
import com.support.customer.lands.viewcontroller.auth.LoginActivity
import com.support.customer.lands.viewcontroller.home.ProjectActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(this@SplashActivity
        ) { instanceIdResult ->
            var deviceToken = instanceIdResult?.token

        }
        Handler(Looper.getMainLooper()).postDelayed({
            controlOpenMainScreen()
        }, 2000)

    }


    private fun controlOpenMainScreen() {
        val bundle = intent.extras
        var intent = Intent(this, LoginActivity::class.java)
        UserServices.accessToken?.let {
            intent = Intent(this, ProjectActivity::class.java)
        }

        if (bundle != null) {
            intent.putExtras(bundle)
        }
        intentActivity(intent)


    }

    private fun intentActivity(intent: Intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                or Intent.FLAG_ACTIVITY_CLEAR_TASK
                or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}
