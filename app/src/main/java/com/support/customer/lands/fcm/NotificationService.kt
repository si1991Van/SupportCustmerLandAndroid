package com.support.customer.lands.fcm

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Bundle
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.support.customer.lands.utills.SharePreferencesKeys
import com.support.customer.lands.utills.SharePreferencesLoaders
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import com.support.customer.lands.R
import com.support.customer.lands.services.UserServices
import com.support.customer.lands.utills.CommonUtil
import com.support.customer.lands.utills.IntentDataKeys
import com.support.customer.lands.viewcontroller.home.MainActivity
import java.util.*
import android.app.NotificationChannel
import android.graphics.Color
import android.provider.Settings
import com.support.customer.lands.services.GsonUtil
import com.support.customer.lands.viewcontroller.home.ProjectActivity


object NotificationService {

    var fcmViewModel: FcmViewModel
    var channelId = "0"

    init {
        fcmViewModel = FcmViewModel()
        fcmViewModel.token = SharePreferencesLoaders.getValue(SharePreferencesKeys.NOTIFICATION_TOKEN, null)
    }


    fun saveToken(token: String?) {
        fcmViewModel.token = token
        SharePreferencesLoaders.saveValue(SharePreferencesKeys.NOTIFICATION_TOKEN, token)
    }

    fun sendRegistrationToServer(pContext: Context) {
        //Call Api update Devices
        val refreshedToken = FirebaseInstanceId.getInstance().token ?: return
        fcmViewModel.token = refreshedToken
        fcmViewModel.deviceId = Settings.Secure.getString(pContext.contentResolver, Settings.Secure.ANDROID_ID)
        Log.d("dmg: ", refreshedToken)
        fcmViewModel.updateNotificationToken(onSuccess = {
            saveToken(fcmViewModel.token)
        }, onFailed = {

        })
    }

    private fun makeHomeIntent(pContext: Context, notificationModel: NotificationModel?): Intent {
        val pIntent = Intent(pContext, ProjectActivity::class.java)
        val bundle = Bundle()
        bundle.putString(IntentDataKeys.KEY_NOTIFICATION_SLUG, notificationModel?.slug)
        pIntent.putExtras(bundle)
        return pIntent
    }

    fun showNotification(pContent: Context, pRemoteMessage: RemoteMessage?) {
        val remoteMessageData = pRemoteMessage?.data
        val pNotificationModel = NotificationModel()
        pNotificationModel.slug = if (remoteMessageData?.containsKey("slug")!!) remoteMessageData["slug"]!!.toString() else null
        pNotificationModel.title = if (remoteMessageData.containsKey("title")) remoteMessageData["title"]!!.toString() else null
        pNotificationModel.body = if (remoteMessageData.containsKey("body")) remoteMessageData["body"]!!.toString() else null
        val intent = this.makeHomeIntent(pContent, pNotificationModel)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP
                    or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    or Intent.FLAG_ACTIVITY_NEW_TASK
        )
        if (!UserServices.isLoggedIn()) {
            return
        }
        val contentStyle = CommonUtil.toHtml(pNotificationModel?.body)
        val notificationId = UUID.randomUUID().hashCode()
        val pendingIntent = PendingIntent.getActivity(
            pContent, notificationId, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        //custom show notification
        val mBuilder = NotificationCompat.Builder(pContent, "0")
            .setContentTitle(pNotificationModel.title)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentText(contentStyle)
            .setStyle(NotificationCompat.BigTextStyle().bigText(contentStyle))
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setDefaults(Notification.DEFAULT_ALL)




        val notificationManager = pContent.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            var notificationChannel: NotificationChannel? = notificationManager.getNotificationChannel(channelId)
            if (notificationChannel == null) {
                val importance = NotificationManager.IMPORTANCE_HIGH
                notificationChannel = NotificationChannel(channelId, contentStyle, importance)
                notificationChannel.lightColor = Color.RED
                notificationChannel.enableVibration(true)
                notificationManager.createNotificationChannel(notificationChannel)
            }
        }




        notificationManager.notify(notificationId, mBuilder.build())


    }

}

internal class NotificationModel {
    var title: String? = null
    var slug: String? = null
    var body: String? = null
}