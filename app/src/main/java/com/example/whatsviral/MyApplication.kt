package com.example.whatsviral

import android.app.Application
import com.taboola.android.Taboola
import com.taboola.android.TBLPublisherInfo

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        try {
            // Initialize Taboola SDK with correct publisher ID
            val publisherInfo = TBLPublisherInfo("sdk-tester-rnd")
            Taboola.init(publisherInfo)
            println("Taboola initialized successfully!")
        } catch (e: Exception) {
            println("Taboola initialization failed: ${e.message}")
            e.printStackTrace()
        }
    }
}