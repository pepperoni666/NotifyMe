package com.pepperoni.android.notifyme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        if(googleApiAvailability.isGooglePlayServicesAvailable(applicationContext) != ConnectionResult.SUCCESS){
            GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this)
        }

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                main_text.text = token
                Log.d("TAGGGGGGGGG", token)
            })
    }
}
