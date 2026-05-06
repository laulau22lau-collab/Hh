package com.example.ma3allem

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlin.random.Random

class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val message = getRandomMessage()
        showNotification(message)
        return Result.success()
    }

    private fun getRandomMessage(): String {
        val messages = listOf(
            "يلعن ابوك يا ابن الكلب",
            "انت ابن كلب يا متخلف",
            "تعال كلو وبدي وجعك",
            "بدي وجعك يا رخم",
            "جهز حالك راح تجيك صاعقة",
            "بتروح باليخت وبنيمك ع التخت",
            "يا حمار مين ضايقك وجعتك شي؟؟",
            "انتهت المهلة يا جبان بدي فتحااااك",
            "أهلا بالخاين جاي تندب؟ ولا جاي تلحس بيضاتي",
            "تصفيق لك على الفشل الذريع وبدي افشخك بالنيك",
            "ما تطلع عوجهك عيب",
            " بدي نيكك والزمن دوار .. وانت قاعد مكانك"
        )
        val randomIndex = Random.nextInt(messages.size)
        return messages[randomIndex]
    }

    private fun showNotification(message: String) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "mock_channel"
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "إشعارات التحدي",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("رسالة من مجد معلمك")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}
