package com.pepperoni.android.notifyme

import android.app.Application
import com.google.firebase.auth.FirebaseAuth

class NotifyMeApplication: Application() {

    lateinit var auth: FirebaseAuth private set

    override fun onCreate() {
        super.onCreate()

        auth = FirebaseAuth.getInstance()
    }
}