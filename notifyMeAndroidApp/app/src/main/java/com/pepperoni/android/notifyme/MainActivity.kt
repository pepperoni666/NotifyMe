package com.pepperoni.android.notifyme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var app: NotifyMeApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        app = application as NotifyMeApplication
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = app.auth.currentUser
        //updateUI(currentUser)
    }

    override fun onResume() {
        super.onResume()
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        if(googleApiAvailability.isGooglePlayServicesAvailable(applicationContext) != ConnectionResult.SUCCESS){
            GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this)
        }

        app.auth.signInAnonymously()
            .addOnCompleteListener(this) { authTask ->
                if (authTask.isSuccessful) {
                    FirebaseInstanceId.getInstance().instanceId
                        .addOnCompleteListener { idTask ->
                            if (idTask.isSuccessful) {
                                idTask.result?.token?.let {
                                    app.db.collection("receivers").document(it).set({
                                        val test = "test"
                                    })
                                }
                            }

                            // Get new Instance ID token
                            val token = idTask.result?.token

                            // Log and toast
                            main_text.text = SpannableStringBuilder(token)
                        }
                    // Sign in success, update UI with the signed-in user's information
                    //Log.d(TAG, "signInAnonymously:success")
                    val user = app.auth.currentUser
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    //Log.w(TAG, "signInAnonymously:failure", authTask.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }

                // ...
            }
    }
}
