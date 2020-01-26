package com.pepperoni.android.notifyme

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class NotifyMeApplication: Application() {

    lateinit var auth: FirebaseAuth private set
    lateinit var db: FirebaseFirestore private set

    override fun onCreate() {
        super.onCreate()

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
    }
}