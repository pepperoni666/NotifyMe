package com.pepperoni.android.notifyem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

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
    }
}
