package com.support.customer.lands.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CustomFirebaseMessagingService : FirebaseMessagingService(){

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        NotificationService.showNotification(this, remoteMessage)
    }

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
    }
}