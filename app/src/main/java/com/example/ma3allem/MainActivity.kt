package com.example.ma3allem

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }

        startNotifications()

        findViewById<Button>(R.id.btnStart).setOnClickListener {
            startNotifications()
            Toast.makeText(this, "تم تشغيل الإشعارات كل نصف ساعة", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startNotifications() {
        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
            30, TimeUnit.MINUTES
        ).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "mock_notifications",
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }
}
