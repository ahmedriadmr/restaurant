package com.doubleclick.restaurant.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.dialog.dialog.AlertDialogReceiveOrderDone
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject


class MyFirebaseMessagingService : FirebaseMessagingService() {

    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ

        // Check if message contains a data payload.
        Log.d(TAG, "Message data payload: ${remoteMessage.data}")

//        if (remoteMessage.data.isNotEmpty()) {

        val body: String? = remoteMessage.data["body"]
        val jsonBody = JSONObject(body)
        sendNotification(jsonBody)



//        remoteMessage.notification?.let {
//                sendNotification(it.body)
//            }
//        }
    }

    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {

        println("NEW FCM TOKEN:$token")
    }



    private fun sendNotification(body: JSONObject) {
        val id = body.optString("id")
        val tableNumber = body.optString("table_number")
        val status = body.optString("status")

        val intent = Intent(this, AlertDialogReceiveOrderDone::class.java)
        intent.putExtra("FCM_MESSAGE_TEST", body.optString("table_number"))
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val requestCode = 0
        val pendingIntent = PendingIntent.getActivity(
            this,
            requestCode,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE,
        )


        Handler(Looper.getMainLooper()).post {
            startActivity(intent)
        }
        val notificationText = "Order No. $id on table $tableNumber is $status"

        val channelId = "fcm_default_channel"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.image)
            .setContentTitle(body.optString("order_type"))
            .setContentText(notificationText)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT,
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notificationId = 0
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}